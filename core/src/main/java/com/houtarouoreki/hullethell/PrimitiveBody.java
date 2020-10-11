package com.houtarouoreki.hullethell;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.Viewport;

public abstract class PrimitiveBody {
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();

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

    abstract public void render(Graphics g, Viewport vp, Vector2 viewArea);
}
