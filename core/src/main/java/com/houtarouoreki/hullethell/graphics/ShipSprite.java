package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ShipConfiguration;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Graphics;

public class ShipSprite extends Sprite {
    private final ShipConfiguration c;
    private final HelperCircle helperCircle = new HelperCircle();

    public ShipSprite(ShipConfiguration c) {
        this.c = c;
        add(helperCircle);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (!HulletHellGame.getSettings().debugging.getValue())
            return;
        g.setLineHeight(0);
        g.setColor(new Color(0f, 1f, 1f, 0.8f));
        for (CollisionCircle circle : c.collisionCircles) {
            drawCircle(g, circle.getX(), circle.getY(), circle.getRadius());
        }

        if (helperCircle.radius == 0)
            return;
        g.drawString("x: " + String.format("%.2f", helperCircle.getPosition().x) + "\n"
                        + "y: " + String.format("%.2f", helperCircle.getPosition().y) + "\n"
                        + "r: " + String.format("%.2f", helperCircle.radius) + "\n",
                getRenderPosition().add(getRenderSize()).x, getRenderPosition().add(getRenderSize()).y);
        g.setColor(new Color(1, 0, 0, 0.4f));
        drawCircle(g, helperCircle.getPosition().x, helperCircle.getPosition().y, helperCircle.radius);
        g.setColor(Color.WHITE);
    }

    private void drawCircle(Graphics g, float x, float y, float radius) {
        Vector2 size = getRenderSize();
        Vector2 centerPos = getRenderPosition().add(size.scl(0.5f));
        g.fillCircle(centerPos.x + x / c.size.x * size.x,
                centerPos.y - (y / c.size.y * size.y),
                radius / c.size.y * size.y);
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
    }

    private static class HelperCircle extends Container {
        public float radius;

        @Override
        public void draw(Graphics g) {
            super.draw(g);
        }

        @Override
        protected void onUpdate(float delta) {
            super.onUpdate(delta);
            if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN))
                setX(getPosition().x + 0.5f * delta);
            if (Gdx.input.isKeyPressed(Input.Keys.FORWARD_DEL))
                setX(getPosition().x - 0.5f * delta);
            if (Gdx.input.isKeyPressed(Input.Keys.HOME))
                setY(getPosition().y + 0.5f * delta);
            if (Gdx.input.isKeyPressed(Input.Keys.END))
                setY(getPosition().y - 0.5f * delta);
            if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP))
                radius += 0.5f * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.INSERT))
                radius -= 0.5f * delta;
            if (radius < 0)
                radius = 0;
        }
    }
}
