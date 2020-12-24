package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.bindables.BindableNumber;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;
import com.houtarouoreki.hullethell.helpers.BasicObjectListener;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.graphics.Sprite;

public class Ship extends Entity {
    public BasicObjectListener<Item> onItemCollected;
    public BindableNumber<Float> cannonTimeOut;
    public BindableNumber<Float> sprintTimeOut;
    public float maxSpeed;
    private float collisionCooldown;
    private float remainingCollisionCooldown = 0;
    private float remainingCollisionCooldownAnimation = 0;

    public Ship(String configurationName) {
        String path = "ships/" + configurationName;
        BodyConfiguration c = HulletHellGame.getAssetManager()
                .get(path + ".cfg", BodyConfiguration.class);
        addTexture(path + ".png");
        setHealth(c.maxHealth);
        setSize(c.size);
        setCollisionBody(c.collisionCircles);
        setTeam(CollisionTeam.ENEMY);
    }

    public void move(float angleDegrees) {
        setVelocity(new Vector2(0, 1).rotated(-angleDegrees).scl(maxSpeed));
    }

    public void update(float delta) {
        super.update(delta);
        if (remainingCollisionCooldown > 0) {
            remainingCollisionCooldown -= delta;
            remainingCollisionCooldownAnimation -= delta;

            if (spritesLayers.isEmpty())
                return;
            float interpolation = Interpolation.sineIn.apply(remainingCollisionCooldown / collisionCooldown);
            float collisionCooldownAnimation = 0.2f;
            if (remainingCollisionCooldownAnimation <= 0) {
                remainingCollisionCooldownAnimation = collisionCooldownAnimation;
                for (Sprite sprite : spritesLayers.get(0)) {
                    sprite.setAlpha(1f - 0.7f * interpolation);
                }
            } else if (remainingCollisionCooldownAnimation <= 0.5f * collisionCooldownAnimation) {
                for (Sprite sprite : spritesLayers.get(0)) {
                    sprite.setAlpha(0.5f - 0.5f * interpolation);
                }
            }

            if (remainingCollisionCooldown <= 0) {
                setAcceptsCollisions(true);
                for (Sprite sprite : spritesLayers.get(0)) {
                    sprite.setAlpha(1);
                }
            }
        }
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (other instanceof Item) {
            Item item = (Item) other;
            if (onItemCollected != null)
                onItemCollected.onAction(item);
            return;
        }
        if (collisionCooldown > 0) {
            setAcceptsCollisions(false);
            remainingCollisionCooldown = collisionCooldown;
            for (Sprite sprite : spritesLayers.get(0)) {
                sprite.setAlpha(0.5f);
            }
        }
    }

    public float getCollisionCooldown() {
        return collisionCooldown;
    }

    public void setCollisionCooldown(float collisionCooldown) {
        this.collisionCooldown = collisionCooldown;
    }

    @Override
    public void applyDamage(float damage) {
        super.applyDamage(damage);
//        if (!isAlive()) {
//            for (Bullet bullet : registeredBullets) {
//                bullet.applyDamage(bullet.getHealth());
//            }
//        }
    }
}
