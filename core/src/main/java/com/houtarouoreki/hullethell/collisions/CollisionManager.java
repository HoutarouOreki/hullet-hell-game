package com.houtarouoreki.hullethell.collisions;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    public final List<CollisionResult> collisions;
    private final World world;

    public CollisionManager(World world) {
        this.world = world;
        collisions = new ArrayList<CollisionResult>();
    }

    public void RunCollisions() {
        for (int i = 0; i < world.bodies.size() - 1; i++) {
            Body a = world.bodies.get(i);
            if (!a.isAcceptingCollisions()) {
                continue;
            }

            for (int j = i + 1; j < world.bodies.size(); j++) {
                Body b = world.bodies.get(j);
                if (!a.isAcceptingCollisions() || !b.isAcceptingCollisions() || a.getTeam() == b.getTeam()) {
                    continue;
                }

                float centerDistance = new Vector2(a.getPosition()).add(new Vector2(b.getPosition()).scl(-1)).len();

                if (centerDistance <= a.getFarthestPointDistance() + b.getFarthestPointDistance()) {
                    CollisionResult collision = isCollision(a, b);
                    if (collision.isCollision) {
                        collisions.add(collision);
                        a.onCollision(b, collision);
                        b.onCollision(a, collision);
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
                    collision.position = approximateCollisionPosition(a, b, ca, cb);
                    collision.time = world.totalTimePassed;
                    return collision;
                }
            }
        }
        return collision;
    }

    private Vector2 approximateCollisionPosition(Body a, Body b, CollisionCircle ca, CollisionCircle cb) {
        return getCollisionCircleWorldPosition(a, ca).add(getCollisionCircleWorldPosition(b, cb)).scl(0.5f);
    }

    private Vector2 getCollisionCircleWorldPosition(Body body, CollisionCircle circle) {
        return new Vector2(body.getPosition().x + circle.getX(), body.getPosition().y + circle.getY());
    }
}
