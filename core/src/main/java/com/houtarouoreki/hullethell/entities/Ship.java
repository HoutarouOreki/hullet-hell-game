package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Ship extends Entity {
    private final List<Bullet> registeredBullets = new ArrayList<Bullet>();
    private float collisionCooldown;
    private float remainingCollisionCooldown = 0;
    private float remainingCollisionCooldownAnimation = 0;

    public Ship(String configurationName) {
        String path = "ships/" + configurationName;
        BodyConfiguration c = HulletHellGame.getAssetManager()
                .get(path + ".cfg", BodyConfiguration.class);
        setTextureName(path + ".png");
        setHealth(c.getMaxHealth());
        setSize(new Vector2(c.getSize()));
        setCollisionBody(c.getCollisionCircles());
    }

    @Override
    public void physics(float delta, Vector2 viewArea) {
        super.physics(delta, viewArea);
        if (remainingCollisionCooldown > 0) {
            remainingCollisionCooldown -= delta;
            remainingCollisionCooldownAnimation -= delta;

            float interpolation = Interpolation.sineIn.apply(remainingCollisionCooldown / collisionCooldown);
            float collisionCooldownAnimation = 0.2f;
            if (remainingCollisionCooldownAnimation <= 0) {
                remainingCollisionCooldownAnimation = collisionCooldownAnimation;
                sprite.setAlpha(1f - 0.7f * interpolation);
            } else if (remainingCollisionCooldownAnimation <= 0.5f * collisionCooldownAnimation) {
                sprite.setAlpha(0.5f - 0.5f * interpolation);
            }

            if (remainingCollisionCooldown <= 0) {
                setAcceptsCollisions(true);
                sprite.setAlpha(1);
            }
        }
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (collisionCooldown > 0) {
            setAcceptsCollisions(false);
            remainingCollisionCooldown = collisionCooldown;
            sprite.setAlpha(0.5f);
        }
    }

    public float getCollisionCooldown() {
        return collisionCooldown;
    }

    public void setCollisionCooldown(float collisionCooldown) {
        this.collisionCooldown = collisionCooldown;
    }

    public void registerBullet(Bullet bullet) {
        registeredBullets.add(bullet);
        bullet.setSource(this);
    }

    public void unregisterBullet(Bullet bullet) {
        registeredBullets.remove(bullet);
    }

    @Override
    public void applyDamage(float damage) {
        super.applyDamage(damage);
        if (!isAlive()) {
            for (Bullet bullet : registeredBullets) {
                bullet.applyDamage(bullet.getHealth());
            }
        }
    }
}
