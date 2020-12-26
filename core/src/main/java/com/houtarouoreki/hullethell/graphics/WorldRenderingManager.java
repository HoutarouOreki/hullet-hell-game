package com.houtarouoreki.hullethell.graphics;

import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.entities.Environmental;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.environment.Finishable;
import com.houtarouoreki.hullethell.environment.Updatable;
import org.mini2Dx.core.graphics.Graphics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WorldRenderingManager {
    private final LinkedList<BulletIndicator> bulletIndicators = new LinkedList<>();
    private final LinkedList<Bullet> bullets = new LinkedList<>();
    private final LinkedList<Ship> ships = new LinkedList<>();
    private final LinkedList<Body> otherBodies = new LinkedList<>();
    private final LinkedList<CollisionIndicator> collisions = new LinkedList<>();
    private final LinkedList<ExplosionEffect> explosionEffects = new LinkedList<>();

    public void update(float delta) {
        updateList(delta, bulletIndicators);
        updateList(delta, collisions);
        updateList(delta, explosionEffects);
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
        for (ExplosionEffect explosionEffect : explosionEffects)
            explosionEffect.render(g);
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
            removeShip((Ship) body);
        else if (body instanceof Bullet)
            bullets.remove(body);
        else if (body instanceof Environmental)
            onRemoveEnvironmental((Environmental) body);
        otherBodies.remove(body);
    }

    private void removeShip(Ship ship) {
        ships.remove(ship);
        if (ship.getHealth() <= 0) {
            explosionEffects.add(new ExplosionEffect(ship.getPosition()));
        }
    }

    private void onRemoveEnvironmental(Environmental environmental) {
        if (environmental.getHealth() <= 0) {
            ExplosionEffect explosionEffect = new ExplosionEffect(environmental.getPosition());
            explosionEffect.setSize(environmental.getSize().scl(2));
            explosionEffects.add(explosionEffect);
        }
    }

    public void addCollision(CollisionResult collisionResult) {
        collisions.add(new CollisionIndicator(collisionResult));
    }

    public void addCollisions(List<CollisionResult> collisionResults) {
        for (CollisionResult collisionResult : collisionResults)
            addCollision(collisionResult);
    }
}
