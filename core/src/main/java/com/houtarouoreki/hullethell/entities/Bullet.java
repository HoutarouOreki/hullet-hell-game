package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;

public class Bullet extends Entity {
    private Ship source;

    public Bullet(String configurationName) {
        String path = "bullets/" + configurationName;
        BodyConfiguration c = HulletHellGame.getAssetManager()
                .get(path + ".cfg", BodyConfiguration.class);
        setTextureName(path + ".png");
        setHealth(c.getMaxHealth());
        setSize(new Vector2(c.getSize()));
        setCollisionBody(c.getCollisionCircles());
        setShouldDespawnOOBounds(true);
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (other instanceof Entity) {
            ((Entity) other).applyDamage(2);
        }
    }

    public void setSource(Ship ship) {
        source = ship;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        source.unregisterBullet(this);
    }
}
