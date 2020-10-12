package com.houtarouoreki.hullethell.environment.collisions;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Body;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.List;

public class CollisionManager {
    private final List<Body> bodies;

    public CollisionManager(List<Body> bodies) {
        this.bodies = bodies;
    }

    public void RunCollisions() {
        for (int i = 0; i < bodies.size() - 1; i++) {
            for (int j = i + 1; j < bodies.size(); j++) {
                Body a = bodies.get(i);
                Body b = bodies.get(j);

                if (!a.isAcceptingCollisions() || !b.isAcceptingCollisions() || a.getTeam() == b.getTeam()) {
                    continue;
                }

                float centerDistance = new Vector2(a.getPosition()).add(new Vector2(b.getPosition()).scl(-1)).len();

                if (centerDistance < a.getFarthestPointDistance() + b.getFarthestPointDistance()) {
                    CollisionResult collision = isCollision(a, b);
                    if (collision.isCollision) {
                        a.onCollision(b, collision);
                        b.onCollision(a, collision);
                        return;
                    }
                }
            }
        }
    }

    private CollisionResult isCollision(Body a, Body b) {
        CollisionResult collision = new CollisionResult();
        for (CollisionCircle ca : a.getCollisionBody()) {
            for (CollisionCircle cb : b.getCollisionBody()) {
                if (Vector2.dst(a.getPosition().x + ca.getX(), a.getPosition().y + ca.getY(),
                        b.getPosition().x + cb.getX(),b.getPosition().y + cb.getY())
                        <= ca.getRadius() + cb.getRadius()) {
                    collision.isCollision = true;
                    return collision;
                }
            }
        }
        return collision;
    }
}
