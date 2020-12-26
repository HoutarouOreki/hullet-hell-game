package com.houtarouoreki.hullethell.collisions;


import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.numbers.Vector2;

public class CollisionResult {
    public final Vector2 position;
    public final float time;
    public final int tick;
    public final Body other;

    public CollisionResult(Vector2 position, float time, int tick, Body other) {
        this.position = position;
        this.time = time;
        this.tick = tick;
        this.other = other;
    }

    public CollisionResult copyWith(Body anotherBody) {
        return new CollisionResult(position, time, tick, anotherBody);
    }
}
