package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.environment.BackgroundObject;
import com.houtarouoreki.hullethell.environment.BackgroundStar;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.environment.collisions.CollisionTeam;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends BasicGameScreen {
    private final List<BackgroundObject> stars = new ArrayList<BackgroundObject>();
    private final AssetManager assetManager;
    private final ScreenManager<? extends GameScreen> screenManager;
    private StageConfiguration script;
    private Ship player;
    private World world;
    private Viewport viewport;

    public PlayScreen(AssetManager assetManager, ScreenManager<? extends GameScreen> screenManager) {
        this.assetManager = assetManager;
        this.screenManager = screenManager;
    }

    public void setStage(StageConfiguration script) {
        this.script = script;
    }

    @Override
    public void preTransitionIn(Transition transitionIn) {
        super.preTransitionIn(transitionIn);
        world = new World(assetManager, script);
        viewport = new FitViewport(1280, 1280 * world.viewArea.y / world.viewArea.x);
        player = new Ship(assetManager, "Ship 1");
        player.setTeam(CollisionTeam.PLAYER);
        player.setPosition(new Vector2(world.viewArea.x * 0.1f, world.viewArea.y * 0.5f));
        world.bodies.add(player);

        initialiseBackground();
    }

    @Override
    public void initialise(GameContainer gc) {
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager, float delta) {
        updateSteering();

        updateBackground(delta);
        world.update(delta);
        clampPlayerPosition();
        if (world.isFinished()) {
            screenManager.enterGameScreen(3, new FadeOutTransition(), new FadeInTransition());
        }
    }

    private void clampPlayerPosition() {
        player.setPosition(
                new Vector2(
                        MathUtils.clamp(player.getPosition().x, 0, world.viewArea.x),
                        MathUtils.clamp(player.getPosition().y, 0, world.viewArea.y)));
    }

    private void updateSteering() {
        float speed = 6;
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

        player.setVelocity(targetVelocity.scl(speed));
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
