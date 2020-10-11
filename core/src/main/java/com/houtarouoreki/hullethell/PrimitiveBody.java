package com.houtarouoreki.hullethell;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.Viewport;

public abstract class PrimitiveBody {
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private Vector2 size = new Vector2();

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void physics(float delta, Vector2 viewArea) {
        getPosition().add(new Vector2(getVelocity()).scl(delta));
    }

    public Vector2 getRenderPosition(Viewport vp, Vector2 viewArea) {
        return new Vector2(position.x, viewArea.y - position.y).scl(vp.getWidth(), vp.getHeight()).scl(1 / viewArea.x, 1 / viewArea.y);
    }

    public Vector2 getRenderSize(Viewport vp, Vector2 viewArea) {
        return new Vector2(size).scl(vp.getWidth(), vp.getHeight()).scl(1 / viewArea.x, 1 / viewArea.y);
    }

    abstract public void render(Graphics g, Viewport vp, Vector2 viewArea);

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }
}
