package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.List;

public class Entity extends Body {
    public float health;
    public EntityTeam team;

    public Entity(Vector2 pos, Vector2 vel, List<CollisionCircle> collisionBody) {
        super(pos, vel, collisionBody);
    }
}
