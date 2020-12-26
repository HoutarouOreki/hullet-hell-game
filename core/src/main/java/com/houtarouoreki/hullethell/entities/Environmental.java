package com.houtarouoreki.hullethell.entities;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;

public class Environmental extends Entity {
    public Environmental(String configurationName) {
        String path = "environmentals/" + configurationName;
        BodyConfiguration c = HulletHellGame.getAssetManager()
                .get(path + ".hhc", BodyConfiguration.class);
        configuration = c;
        setHealth(c.maxHealth);
        setSize(c.size);
        getCollisionBodyManager().setCollisionBody(c.collisionCircles);
        getCollisionBodyManager().setTeam(CollisionTeam.ENVIRONMENT);
        setShouldDespawnOOBounds(true);
    }

    @Override
    public void onCollision(CollisionResult collision) {
        super.onCollision(collision);
        if (collision.other instanceof Entity) {
            ((Entity) collision.other).applyDamage(2);
        }
    }

    public void update(float delta) {
        super.update(delta);
        if (spritesLayers.isEmpty())
            return;
        spritesLayers.get(0).rotation += 40 * delta;
    }
}
