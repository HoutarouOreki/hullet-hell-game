package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.environment.collisions.CollisionResult;
import com.houtarouoreki.hullethell.environment.collisions.CollisionTeam;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.ArrayList;
import java.util.List;

public class Environmental extends Entity {
    public Environmental(AssetManager assetManager, float size) {
        super(assetManager, generateCollisionBody(size));
        setTeam(CollisionTeam.ENVIRONMENT);
        setHealth(size);
    }

    private static List<CollisionCircle> generateCollisionBody(float size) {
        List<CollisionCircle> collisionBody = new ArrayList<CollisionCircle>();
        collisionBody.add(new CollisionCircle(0, 0, size / 2));
        return collisionBody;
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (other instanceof Entity) {
            ((Entity) other).applyDamage(2);
        }
    }

    @Override
    public void physics(float delta, Vector2 viewArea) {
        super.physics(delta, viewArea);
        sprite.rotate(2);
    }
}
