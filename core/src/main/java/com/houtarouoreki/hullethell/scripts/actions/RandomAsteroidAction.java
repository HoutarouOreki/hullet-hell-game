package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Environmental;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

import java.util.Arrays;
import java.util.Random;

public class RandomAsteroidAction extends ScriptedAction {
    private Environmental asteroid;

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        Random random = new Random();
        Vector2 velocity = new Vector2(
                random.nextFloat() * 6 + 3,
                random.nextFloat() * 2.2f - 1.1f
        );
        Vector2 startingPosition = new Vector2(
                1.2f,
                random.nextFloat()
        );
        if (arguments.size() == 0)
            return;

        String[] itemStrings = arguments.get(0).split(" / ");

        asteroid = new Environmental("Asteroid");
        asteroid.setVelocity(velocity);
        asteroid.setPosition(startingPosition);
        asteroid.itemDrops.addAll(Arrays.asList(itemStrings));
    }

    @Override
    protected void performAction() {
        if (getTicks() == 0)
            world.addBody(asteroid);
        if (asteroid.getPosition().x < -0.2 || !asteroid.isAlive()) {
            setFinished();
        }
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
