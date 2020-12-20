package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.environment.World;

import java.util.Random;

public class RandomAsteroid extends Environmental {
    public RandomAsteroid(float scale) {
        super("Asteroid");
        Random random = new Random();
        Vector2 velocity = new Vector2(
                -(random.nextFloat() * 6 + 3),
                random.nextFloat() * 2.2f - 1.1f
        );
        setHealth(10 * scale);
        setVelocity(velocity.scl(1 / scale));
        scale(scale);
        Vector2 startingPosition = new Vector2(
                1,
                random.nextFloat()
        ).scl(World.viewArea).add(getSize().scl(new Vector2(0.5f, 0)));
        setPosition(startingPosition);
    }

    public RandomAsteroid() {
        this(new Random().nextFloat() * 2 + 0.5f);
    }
}
