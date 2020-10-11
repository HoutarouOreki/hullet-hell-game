package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.EntityTeam;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.environment.BackgroundObject;
import com.houtarouoreki.hullethell.environment.BackgroundStar;
import com.houtarouoreki.hullethell.environment.World;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends BasicGameScreen {
    private final Vector2 viewArea = new Vector2(36, 20);
    private final List<BackgroundObject> stars = new ArrayList<BackgroundObject>();
    private Ship player;
    private World world;
    private Viewport viewport;
    private float enemySpawnTimer;

    @Override
    public void initialise(GameContainer gc) {
        viewport = new FitViewport(1280, 1280 * viewArea.y / viewArea.x);
        player = new Ship(new ArrayList<CollisionCircle>() {{
            add(new CollisionCircle(-0.2f, -0.1f, 0.55f));
            add(new CollisionCircle(0.8f, -0.35f, 0.3f));
        }});
        player.setTeam(EntityTeam.PLAYER);
        player.setTextureName("playerShip.png");
        player.setSize(new Vector2(3.2f, 1.3f));
        world = new World();
        world.bodies.add(player);

        initialiseBackground();
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager, float delta) {
        updateSteering();

        updateBackground(delta);
        world.update(delta, viewArea);
        clampPlayerPosition();
        attemptSpawnEnemy(delta);
    }

    private void attemptSpawnEnemy(float delta) {
        enemySpawnTimer += delta;
        float enemySpawnFrequency = 0.4f;
        if (enemySpawnTimer > enemySpawnFrequency) {
            enemySpawnTimer -= enemySpawnFrequency;
            spawnEnemy();
        }
    }

    private void spawnEnemy() {
        final float enemySize = 4 + (float) Math.random() * 4;
        Ship enemy = new Ship(new ArrayList<CollisionCircle>() {
            {
                add(new CollisionCircle(0, 0, enemySize / 2));
            }
        });
        enemy.setTextureName("asteroida.png");
        enemy.setSize(new Vector2(enemySize, enemySize));
        enemy.setPosition(new Vector2(viewArea.x + enemy.getSize().x / 2, (float) Math.random() * viewArea.y));
        enemy.setVelocity(new Vector2(-12, 0));
        world.bodies.add(enemy);
    }

    private void clampPlayerPosition() {
        player.getPosition().x = MathUtils.clamp(player.getPosition().x, 0, viewArea.x);
        player.getPosition().y = MathUtils.clamp(player.getPosition().y, 0, viewArea.y);
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

        player.getVelocity().set(targetVelocity.scl(speed));
    }

    private void initialiseBackground() {
        float starsAmount = 200;
        final float minStarSize = 0.04f;
        final float maxStarSize = 0.07f;
        for (int i = 0; i < starsAmount; i++) {
            float starSize = minStarSize + (float) Math.random() * (maxStarSize - minStarSize);
            BackgroundStar star = new BackgroundStar((float) Math.random() * viewArea.x,
                    (float) Math.random() * viewArea.y, starSize);
            star.setVelocity(new Vector2(-1, 0));
            stars.add(star);
        }
    }

    private void updateBackground(float delta) {
        for (BackgroundObject star : stars) {
            star.physics(delta, viewArea);
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
        renderBodies(g);
    }

    private void drawBackground(Graphics g) {
        for (BackgroundObject star : stars) {
            star.render(g, viewport, viewArea);
        }
    }

    @Override
    public int getId() {
        return 2;
    }

    private void renderBodies(Graphics g) {
        for (Body body : world.bodies) {
            body.render(g, viewport, viewArea);
        }
    }
}
