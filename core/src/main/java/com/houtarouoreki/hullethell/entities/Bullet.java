package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;
import com.houtarouoreki.hullethell.environment.collisions.CollisionResult;

public class Bullet extends Entity {
    public Bullet(AssetManager assetManager, String configurationName) {
        super(assetManager);
        String path = "bullets/" + configurationName;
        BodyConfiguration c = assetManager.get(path + ".cfg", BodyConfiguration.class);
        setTextureName(path + ".png");
        setHealth(c.getMaxHealth());
        setSize(new Vector2(c.getSize()));
        setCollisionBody(c.getCollisionCircles());
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (other instanceof Entity) {
            ((Entity) other).applyDamage(2);
        }
    }
}
