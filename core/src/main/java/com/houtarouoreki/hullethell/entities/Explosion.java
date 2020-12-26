package com.houtarouoreki.hullethell.entities;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.Circle;
import com.houtarouoreki.hullethell.collisions.CollisionBodyManager;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.numbers.Vector2;

import java.util.Collections;

public class Explosion extends Entity {
    private final float damage;
    private final float duration;

    public Explosion(float damage, float radius, float duration) {
        this.damage = damage;
        this.duration = duration;
        setSize(new Vector2(radius * 2));
        getCollisionBodyManager().setCollisionBody(Collections
                .singletonList(new Circle(Vector2.ZERO, radius)));
        addAnimation("effects/Explosion", 6, 6 / duration);
        getCollisionBodyManager().setTeam(CollisionTeam.ENVIRONMENT);
    }

    @Override
    public boolean isAlive() {
        return getTime() <= duration;
    }

    @Override
    public void onCollision(CollisionResult collision) {
        if (collision.other instanceof Entity) {
            ((Entity) collision.other).applyDamage(damage);
            getCollisionBodyManager().disableCollisionsWith(collision.other);
        }
    }

    @Override
    protected CollisionBodyManager generateCollisionBodyManager() {
        return new ExplosionCollisionBodyManager(this);
    }

    @Override
    public void update(float delta) {
        if (getTime() == 0)
            HulletHellGame.getSoundManager().playSound("kick-gritty");
        super.update(delta);
    }

    private class ExplosionCollisionBodyManager extends CollisionBodyManager {
        public ExplosionCollisionBodyManager(Explosion body) {
            super(body);
        }

        @Override
        public boolean isAcceptingCollisions() {
            return super.isAcceptingCollisions() && getTicks() <= 1;
        }
    }
}
