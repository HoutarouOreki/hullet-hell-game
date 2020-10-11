package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.List;

public class Ship extends Entity {
    public Ship(Vector2 pos, Vector2 vel, List<CollisionCircle> collisionBody) {
        super(pos, vel, collisionBody);
    }
}
