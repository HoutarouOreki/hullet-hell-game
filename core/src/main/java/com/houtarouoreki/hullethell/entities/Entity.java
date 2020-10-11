package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.List;

public class Entity extends Body {
    private float health;
    private EntityTeam team;

    public Entity(List<CollisionCircle> collisionBody) {
        super(collisionBody);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public EntityTeam getTeam() {
        return team;
    }

    public void setTeam(EntityTeam team) {
        this.team = team;
    }
}
