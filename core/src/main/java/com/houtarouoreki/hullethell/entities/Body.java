package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.List;

public class Body {
    public final List<CollisionCircle> collisionBody;
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();
    public String textureName;

    public Body(Vector2 pos, Vector2 vel, List<CollisionCircle> collisionBody) {
        position = pos;
        velocity = vel;
        this.collisionBody = collisionBody;
    }

    public void physics(float delta) {
        position.add(new Vector2(velocity).scl(delta));
        velocity.add(new Vector2(acceleration).scl(delta));
    }

    public float getFarthestPointDistance() {
        float farthestDistance = 0;
        for (CollisionCircle circle : collisionBody) {
            float distance = circle.getDistanceFromCenter(0, 0) + circle.getRadius();
            if (distance > farthestDistance)
                farthestDistance = distance;
        }
        return farthestDistance;
    }
}
