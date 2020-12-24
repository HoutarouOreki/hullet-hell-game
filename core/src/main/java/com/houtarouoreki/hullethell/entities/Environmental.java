package com.houtarouoreki.hullethell.entities;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;

public class Environmental extends Entity {
    public Environmental(String configurationName) {
        String path = "environmentals/" + configurationName;
        BodyConfiguration c = HulletHellGame.getAssetManager()
                .get(path + ".cfg", BodyConfiguration.class);
        addTexture(path + ".png");
        setHealth(c.maxHealth);
        setSize(c.size);
        setCollisionBody(c.collisionCircles);
        setTeam(CollisionTeam.ENVIRONMENT);
        setShouldDespawnOOBounds(true);
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (other instanceof Entity) {
            ((Entity) other).applyDamage(2);
        }
    }

    public void update(float delta) {
        super.update(delta);
        if (spritesLayers.isEmpty())
            return;
        spritesLayers.get(0).rotation += 40 * delta;
    }
}
