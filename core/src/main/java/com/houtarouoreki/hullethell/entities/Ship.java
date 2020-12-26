package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.bindables.BindableNumber;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.configurations.ShipConfiguration;
import com.houtarouoreki.hullethell.helpers.BasicObjectListener;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.graphics.Sprite;

public class Ship extends Entity {
    private final boolean shootsExplosives;
    private final String ammunitionName;
    private final float ammunitionSpeed;
    public BasicObjectListener<Item> onItemCollected;
    public BindableNumber<Float> cannonTimeOut;
    public BindableNumber<Float> sprintTimeOut;
    public float maxSpeed;
    private float collisionCooldown;
    private float remainingCollisionCooldown = 0;
    private float remainingCollisionCooldownAnimation = 0;

    public Ship(String configurationName) {
        String path = "ships/" + configurationName;
        ShipConfiguration c = HulletHellGame.getAssetManager().get(path + ".cfg");
        configuration = c;
        maxSpeed = c.maxSpeed;
        cannonTimeOut = new BindableNumber<>(0f, 0f, c.cannonTimeOut);
        sprintTimeOut = new BindableNumber<>(0f, 0f, c.sprintTimeOut);
        setHealth(c.maxHealth);
        setSize(c.size);
        collisionBodyManager.setCollisionBody(c.collisionCircles);
        collisionBodyManager.setTeam(CollisionTeam.ENEMY);
        shootsExplosives = c.ammunitionType.equals("explosives");
        ammunitionName = c.ammunitionName;
        ammunitionSpeed = c.ammunitionSpeed;
    }

    public void move(float angleDegrees) {
        setVelocity(new Vector2(0, 1).rotated(angleDegrees, true).scl(maxSpeed));
    }

    public void stop() {
        setVelocity(new Vector2());
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
                collisionBodyManager.setAcceptsCollisions(true);
                for (Sprite sprite : spritesLayers.get(0)) {
                    sprite.setAlpha(1);
                }
            }
        }
        cannonTimeOut.setValue(cannonTimeOut.getValue() - delta);
        sprintTimeOut.setValue(sprintTimeOut.getValue() - delta);
    }

    @Override
    public void onCollision(CollisionResult collision) {
        super.onCollision(collision);
        if (collision.other instanceof Item) {
            Item item = (Item) collision.other;
            if (onItemCollected != null)
                onItemCollected.onAction(item);
            return;
        }
        if (collisionCooldown > 0) {
            collisionBodyManager.setAcceptsCollisions(false);
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

    public Entity shoot() {
        if (cannonTimeOut.getValue() != 0)
            return null;

        cannonTimeOut.setMaxValue();
        Entity entity = shootsExplosives ?
                new Explosive(ammunitionName) : new Bullet(ammunitionName);
        entity.setPosition(getPosition());
        entity.setVelocity(new Vector2(ammunitionSpeed, 0));
        return entity;
    }
}
