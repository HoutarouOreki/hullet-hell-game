package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
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

    @Override
    public void initialise(GameContainer gc) {
        viewport = new FitViewport(1280, 1280 * viewArea.y / viewArea.x);
        player = new Ship(new ArrayList<CollisionCircle>() {{
            add(new CollisionCircle(0, 0, 1.5f));
            add(new CollisionCircle(1.5f, 0, 1));
            add(new CollisionCircle(0, 1, 1));
        }});
        player.setTeam(EntityTeam.PLAYER);
        world = new World();
        world.bodies.add(player);

        initialiseBackground();
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager, float delta) {
//        player.acceleration.x = 0;
//        player.acceleration.y = 0;
        //player.velocity.set(0, 0);
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

        updateBackground(delta);
        world.update(delta, viewArea);
    }

    private void initialiseBackground() {
        float starsAmount = 1000;
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
