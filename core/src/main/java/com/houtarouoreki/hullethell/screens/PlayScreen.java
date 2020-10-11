package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.environment.World;
import org.apache.commons.lang3.StringUtils;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;

import java.util.ArrayList;

public class PlayScreen extends BasicGameScreen {
    private final Vector2 viewArea = new Vector2(30, 20);
    private Ship player;
    private World world;
    private Viewport viewport;

    public PlayScreen() {
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager, float delta) {
        player.acceleration.x = 0;
        player.acceleration.y = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            player.acceleration.x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            player.acceleration.x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            player.acceleration.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            player.acceleration.y += 1;

        player.acceleration.nor();

        world.update(delta);
    }

    @Override
    public void initialise(GameContainer gc) {
        viewport = new FitViewport(1280, 1280 * viewArea.y / viewArea.x);
        player = new Ship(new Vector2(15, 10), new Vector2(0, 2), new ArrayList<CollisionCircle>() {{
            add(new CollisionCircle(0, 0, 1.5f));
            add(new CollisionCircle(1.5f, 0, 1));
            add(new CollisionCircle(0, 1, 1));
        }});
        world = new World();
        world.bodies.add(player);
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {

    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        // tutaj HUD
        viewport.apply(g);
        drawBodies(gc, g);
    }

    @Override
    public int getId() {
        return 2;
    }

    private void drawBodies(GameContainer gc, Graphics g) {
        for (Body body : world.bodies) {
            drawBody(body, gc, g);
        }
    }

    private void drawBody(Body body, GameContainer gc, Graphics g) {
        if (StringUtils.isEmpty(body.textureName)) {
            for (CollisionCircle circle : body.collisionBody) {
                float scaleX = gc.getWidth() / viewArea.x;
                float scaleY = gc.getHeight() / viewArea.y;
                float scale = Math.min(scaleX, scaleY);
                g.drawCircle((body.position.x + circle.getX()) * scale,
                        (viewArea.y - (body.position.y + circle.getY())) * scale,
                        circle.getRadius() * scale);
            }
        } else {
            g.drawTexture(new Texture(body.textureName), body.position.x, body.position.y,
                    body.getFarthestPointDistance() * 2, body.getFarthestPointDistance() * 2);
        }
    }
}
