package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.environment.BackgroundObject;
import com.houtarouoreki.hullethell.environment.BackgroundStar;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.input.Controls;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends HulletHellScreen {
    private final List<BackgroundObject> stars = new ArrayList<BackgroundObject>();
    private StageConfiguration script;
    private Ship player;
    private World world;
    private Viewport viewport;
    private int shotFrames;

    public void setStage(StageConfiguration script) {
        this.script = script;
    }

    @Override
    public void preTransitionIn(Transition transitionIn) {
        super.preTransitionIn(transitionIn);
        world = new World(script);
        viewport = new FitViewport(1280,
                1280 * world.viewArea.y / world.viewArea.x);
        player = new Ship("Ship 1");
        player.setCollisionCooldown(2);
        player.setTeam(CollisionTeam.PLAYER);
        player.setPosition(new Vector2(world.viewArea.x * 0.1f, world.viewArea.y * 0.5f));
        world.bodies.add(player);

        initialiseBackground();
    }

    @Override
    public void preTransitionOut(Transition transitionOut) {
        super.preTransitionOut(transitionOut);
        HulletHellGame.getMusicManager().fadeOut(2);
    }

    @Override
    public void initialise(GameContainer gc) {
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager,
                       float delta) {
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
        if (HulletHellGame.getInputManager().isControlActive(Controls.left))
            targetVelocity.x -= 1;
        if (HulletHellGame.getInputManager().isControlActive(Controls.right))
            targetVelocity.x += 1;
        if (HulletHellGame.getInputManager().isControlActive(Controls.down))
            targetVelocity.y -= 1;
        if (HulletHellGame.getInputManager().isControlActive(Controls.up))
            targetVelocity.y += 1;
        if (HulletHellGame.getInputManager().isControlActive(Controls.shoot)) {
            shotFrames++;
            if (shotFrames % 4 == 0) {
                Bullet bullet = new Bullet("Player bullet 1");
                world.bodies.add(bullet);
                bullet.setPosition(player.getPosition());
                bullet.setTeam(CollisionTeam.PLAYER);
                bullet.setVelocity(new Vector2(40, 0));
                player.registerBullet(bullet);
                HulletHellGame.getSoundManager()
                        .playSound("laser1", 0.3f);
            }
        }
        if (HulletHellGame.getInputManager().isControlActive(Controls.back))
            HulletHellGame.getScreensManager().enterGameScreen(1,
                    new FadeOutTransition(), new FadeInTransition());

        player.setVelocity(targetVelocity.scl(speed));
    }

    private void initialiseBackground() {
        float starsAmount = 200;
        final float minStarSize = 0.04f;
        final float maxStarSize = 0.07f;
        for (int i = 0; i < starsAmount; i++) {
            float starSize = minStarSize + (float) Math.random() * (maxStarSize - minStarSize);
            BackgroundStar star = new BackgroundStar((float) Math.random() * world.viewArea.x,
                    (float) Math.random() * world.viewArea.y, starSize);
            star.setVelocity(new Vector2((float)Math.random() * -0.2f - 0.2f, 0));
            stars.add(star);
        }
    }

    private void updateBackground(float delta) {
        for (BackgroundObject star : stars) {
            star.physics(delta, world.viewArea);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        // tutaj HUD
        viewport.apply(g);
        if (HulletHellGame.getSettings().backgrounds.getValue())
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
        return PLAY_SCREEN;
    }
}
