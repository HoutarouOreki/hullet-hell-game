package com.houtarouoreki.hullethell.graphics;

import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.environment.Finishable;
import com.houtarouoreki.hullethell.environment.Updatable;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldRenderingManager {
    private final List<BulletIndicator> bulletIndicators = new ArrayList<BulletIndicator>();
    private final List<Bullet> bullets = new ArrayList<Bullet>();
    private final List<Ship> ships = new ArrayList<Ship>();
    private final List<Body> otherBodies = new ArrayList<Body>();
    private final List<CollisionIndicator> collisions = new ArrayList<CollisionIndicator>();

    public void update(float delta) {
        updateList(delta, bulletIndicators);
        updateList(delta, collisions);
    }

    private <E extends Updatable & Finishable> void updateList(float delta, List<E> list) {
        Iterator<E> i = list.iterator();
        while (i.hasNext()) {
            E something = i.next();
            something.update(delta);
            if (something.isDone())
                i.remove();
        }
    }

    public void render(Graphics g) {
        for (Body otherBody : otherBodies)
            otherBody.render(g);
        for (Bullet bullet : bullets)
            bullet.render(g);
        for (Ship ship : ships)
            ship.render(g);
        for (CollisionIndicator collision : collisions)
            collision.render(g);
        for (BulletIndicator bulletIndicator : bulletIndicators)
            bulletIndicator.render(g);
    }

    public void registerBody(Body body) {
        if (body instanceof Ship)
            ships.add((Ship) body);
        else if (body instanceof Bullet) {
            bullets.add((Bullet) body);
            bulletIndicators.add(new BulletIndicator((Bullet) body));
        }
        otherBodies.add(body);
    }

    public void unregisterBody(Body body) {
        if (body instanceof Ship)
            ships.remove(body);
        else if (body instanceof Bullet)
            bullets.remove(body);
        otherBodies.remove(body);
    }

    public void addCollision(CollisionResult collisionResult) {
        collisions.add(new CollisionIndicator(collisionResult));
    }

    public void addCollisions(List<CollisionResult> collisionResults) {
        for (CollisionResult collisionResult : collisionResults)
            addCollision(collisionResult);
    }
}
