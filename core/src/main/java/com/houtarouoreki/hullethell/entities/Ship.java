package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.List;

public class Ship extends Entity {
    public Ship(List<CollisionCircle> collisionBody) {
        super(collisionBody);
    }
}
