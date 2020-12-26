package com.houtarouoreki.hullethell.collisions;

import com.houtarouoreki.hullethell.numbers.Vector2;

public class Circle {
    public final Vector2 position;
    public final float radius;

    public Circle(Vector2 position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public float longestDistanceTo(Vector2 from) {
        return position.dst(from) + radius;
    }

    public float shortestDistanceTo(Vector2 from) {
        return Math.max(0, position.dst(from) - radius);
    }

    public float distanceTo(Circle other) {
        return position.dst(other.position);
    }

    public float shortestDistanceTo(Circle other) {
        return Math.max(0, distanceTo(other) - radius - other.radius);
    }

    public float longestDistanceTo(Circle other) {
        return distanceTo(other) + radius + other.radius;
    }

    public Circle scl(float scale) {
        return new Circle(position.scl(scale), radius * scale);
    }

    public Circle add(Vector2 addPosition) {
        return new Circle(position.add(addPosition), radius);
    }

    public boolean intersectsWith(Circle other) {
        return shortestDistanceTo(other) == 0;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "position=" + position +
                ", radius=" + radius +
                '}';
    }
}
