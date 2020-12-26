package com.houtarouoreki.hullethell.collisions;

import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.numbers.Vector2;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    public final List<CollisionResult> currentStepCollisions;
    private final World world;

    public CollisionManager(World world) {
        this.world = world;
        currentStepCollisions = new ArrayList<>();
    }

    public void RunCollisions() {
        currentStepCollisions.clear();
        for (int i = 0; i < world.getBodies().size() - 1; i++) {
            CollisionBodyManager a = world.getBodies().get(i).getCollisionBodyManager();
            if (!a.isAcceptingCollisions(world.getTicksPassed()))
                continue;

            for (int j = i + 1; j < world.getBodies().size(); j++) {
                CollisionBodyManager b = world.getBodies().get(j).getCollisionBodyManager();

                if (!a.canCollideWith(b, world.getTicksPassed())
                        || !b.canCollideWith(a, world.getTicksPassed())) {
                    continue;
                }

                float bodyCentersDst = a.body.getPosition().dst(b.body.getPosition());

                if (bodyCentersDst <= a.getFarthestPointDistance() + b.getFarthestPointDistance()) {
                    CollisionResult collision = isCollision(a, b);
                    if (collision != null) {
                        currentStepCollisions.add(collision);
                        b.onCollision(collision);
                        a.onCollision(collision.copyWith(b.body));
                    }
                }
            }
        }
    }

    private CollisionResult isCollision(CollisionBodyManager a, CollisionBodyManager b) {
        for (Circle ca : a.getCollisionBodyCopy(true)) {
            for (Circle cb : b.getCollisionBodyCopy(true)) {
                if (ca.intersectsWith(cb)) {
                    Vector2 position = approximateCollisionPosition(ca, cb);
                    float time = world.getTimePassed();
                    int tick = world.getTicksPassed();
                    return new CollisionResult(position, time, tick, a.body);
                }
            }
        }
        return null;
    }

    private Vector2 approximateCollisionPosition(Circle ca, Circle cb) {
        return ca.position.add(cb.position).scl(0.5f);
    }
}
