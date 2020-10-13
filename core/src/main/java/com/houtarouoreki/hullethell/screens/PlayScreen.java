package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Asteroid;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.entities.ai.CpuPlayer;
import com.houtarouoreki.hullethell.environment.BackgroundObject;
import com.houtarouoreki.hullethell.environment.BackgroundStar;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.environment.collisions.CollisionTeam;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends BasicGameScreen {
    private final List<BackgroundObject> stars = new ArrayList<BackgroundObject>();
    private final AssetManager assetManager;
    private final ScreenManager<? extends GameScreen> screenManager;
    private Ship player;
    private World world;
    private Viewport viewport;
    private float asteroidSpawnTimer;

    public PlayScreen(AssetManager assetManager, ScreenManager<? extends GameScreen> screenManager) {
        this.assetManager = assetManager;
        this.screenManager = screenManager;
    }

    @Override
    public void initialise(GameContainer gc) {
        world = new World();
        viewport = new FitViewport(1280, 1280 * world.viewArea.y / world.viewArea.x);
        player = new Ship(assetManager, new ArrayList<CollisionCircle>() {{
            add(new CollisionCircle(-0.2f, -0.1f, 0.55f));
            add(new CollisionCircle(0.8f, -0.35f, 0.3f));
        }});
        player.setTeam(CollisionTeam.PLAYER);
        player.setTextureName("playerShip.png");
        player.setSize(new Vector2(3.2f, 1.3f));
        player.setPosition(new Vector2(world.viewArea.x * 0.1f, world.viewArea.y * 0.5f));
        player.setHealth(40);
        world.bodies.add(player);

        Ship enemyShip = new Ship(assetManager, new ArrayList<CollisionCircle>() {{
            add(new CollisionCircle(0.75f, -0.85f, 0.8f));
            add(new CollisionCircle(-0.5f, 1.55f, 0.05f));
            add(new CollisionCircle(-1.55f, -1.05f, 0.05f));

            add(new CollisionCircle(-0.8f, -1.15f, 0.3f));
            add(new CollisionCircle(0.6f, 1f, 0.3f));
        }});
        enemyShip.setTeam(CollisionTeam.COMPUTER);
        enemyShip.setHealth(61);
        enemyShip.setPosition(new Vector2(world.viewArea.x * 0.8f, world.viewArea.y * 0.5f));
        enemyShip.setTextureName("enemyShip1.png");
        enemyShip.setSize(new Vector2(3.2f, 3.2f));
        world.bodies.add(enemyShip);
        world.cpus.add(new CpuPlayer(enemyShip, world));

        initialiseBackground();
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager, float delta) {
        updateSteering();

        updateBackground(delta);
        world.update(delta);
        clampPlayerPosition();
        attemptSpawnAsteroid(delta);
    }

    private void attemptSpawnAsteroid(float delta) {
        asteroidSpawnTimer += delta;
        float asteroidSpawnFrequency = 2f;
        if (asteroidSpawnTimer > asteroidSpawnFrequency) {
            asteroidSpawnTimer -= asteroidSpawnFrequency;
            spawnAsteroid();
        }
    }

    private void spawnAsteroid() {
        float size = 0.5f + (float) Math.random() * 2;
        Asteroid asteroid = new Asteroid(assetManager, size);
        asteroid.setTextureName("asteroida.png");
        asteroid.setSize(new Vector2(size, size));
        asteroid.setPosition(new Vector2(world.viewArea.x + asteroid.getSize().x / 2, (float) Math.random() * world.viewArea.y));
        asteroid.setVelocity(new Vector2(-4, 0));
        world.bodies.add(asteroid);
    }

    private void clampPlayerPosition() {
        player.getPosition().x = MathUtils.clamp(player.getPosition().x, 0, world.viewArea.x);
        player.getPosition().y = MathUtils.clamp(player.getPosition().y, 0, world.viewArea.y);
    }

    private void updateSteering() {
        float speed = 10;
        Vector2 targetVelocity = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            targetVelocity.x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            targetVelocity.x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            targetVelocity.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            targetVelocity.y += 1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            screenManager.enterGameScreen(1, new FadeOutTransition(), new FadeInTransition());

        player.getVelocity().set(targetVelocity.scl(speed));
    }

    private void initialiseBackground() {
        float starsAmount = 200;
        final float minStarSize = 0.04f;
        final float maxStarSize = 0.07f;
        for (int i = 0; i < starsAmount; i++) {
            float starSize = minStarSize + (float) Math.random() * (maxStarSize - minStarSize);
            BackgroundStar star = new BackgroundStar(assetManager, (float) Math.random() * world.viewArea.x,
                    (float) Math.random() * world.viewArea.y, starSize);
            star.setVelocity(new Vector2(-1, 0));
            stars.add(star);
        }
    }

    private void updateBackground(float delta) {
        for (BackgroundObject star : stars) {
            star.physics(delta, world.viewArea);
        }
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {

    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        // tutaj HUD
        viewport.apply(g);
        drawBackground(g);
        world.render(g, viewport);
    }

    private void drawBackground(Graphics g) {
        for (BackgroundObject star : stars) {
            star.render(g, viewport, world.viewArea);
        }
    }

    @Override
    public int getId() {
        return 2;
    }
}
