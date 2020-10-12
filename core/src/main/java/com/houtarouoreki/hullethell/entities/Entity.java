package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.assets.AssetManager;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.List;

public class Entity extends Body {
    private float health;

    public Entity(AssetManager assetManager, List<CollisionCircle> collisionBody) {
        super(assetManager, collisionBody);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void applyDamage(float damage) {
        this.health -= damage;
    }

    public boolean isAlive() { return health > 0; }
}
