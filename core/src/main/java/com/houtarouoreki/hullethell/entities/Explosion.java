package com.houtarouoreki.hullethell.entities;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

public class Explosion extends Entity {
    private final float damage;
    private final float duration;

    public Explosion(float damage, float radius, float duration) {
        this.damage = damage;
        this.duration = duration;
        setSize(new Vector2(radius * 2));
        getCollisionBody().add(new CollisionCircle(0, 0, radius));
        addAnimation("effects/Explosion", 6, 6 / duration);
    }

    @Override
    public boolean isAlive() {
        return getTime() <= duration;
    }

    @Override
    public boolean isAcceptingCollisions() {
        return super.isAcceptingCollisions() && getTicks() <= 1;
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        if (other instanceof Entity) {
            ((Entity) other).applyDamage(damage);
            dontCollideWith.add(other);
        }
    }

    @Override
    public void update(float delta) {
        if (getTime() == 0)
            HulletHellGame.getSoundManager().playSound("kick-gritty");
        super.update(delta);
    }
}
