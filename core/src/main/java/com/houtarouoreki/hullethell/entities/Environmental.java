package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;
import org.mini2Dx.core.graphics.Sprite;

public class Environmental extends Entity {
    public Environmental(String configurationName) {
        String path = "environmentals/" + configurationName;
        BodyConfiguration c = HulletHellGame.getAssetManager()
                .get(path + ".cfg", BodyConfiguration.class);
        setTextureName(path + ".png");
        setHealth(c.getMaxHealth());
        setSize(new Vector2(c.getSize()));
        setCollisionBody(c.getCollisionCircles());
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
        for (Sprite sprite : sprites) {
            sprite.rotate(2);
        }
    }
}
