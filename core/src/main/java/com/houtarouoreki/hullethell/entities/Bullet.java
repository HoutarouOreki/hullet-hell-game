package com.houtarouoreki.hullethell.entities;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;

public class Bullet extends Entity {
    public Bullet(String configurationName) {
        String path = "bullets/" + configurationName;
        BodyConfiguration c = HulletHellGame.getAssetManager()
                .get(path + ".cfg", BodyConfiguration.class);
        configuration = c;
        setHealth(c.maxHealth);
        setSize(c.size);
        setCollisionBody(c.collisionCircles);
        setShouldDespawnOOBounds(true);
    }

    protected Bullet() {}

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (other instanceof Entity) {
            ((Entity) other).applyDamage(2);
        }
    }
}
