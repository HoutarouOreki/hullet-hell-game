package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.RandomAsteroid;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSplittingAsteroidAction extends ScriptedAction {
    private final List<RandomAsteroid> asteroids = new ArrayList<RandomAsteroid>();
    private final List<RandomAsteroid> toAdd = new ArrayList<RandomAsteroid>();
    private final List<RandomAsteroid> toRemove = new ArrayList<RandomAsteroid>();
    private final float itemDropMaxRadius = 0.5f;
    private Random random;
    private String[] itemStrings;

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        if (arguments.size() == 0)
            return;

        random = new Random();
        itemStrings = arguments.get(0).split(" / ");
    }

    private void addAsteroid(RandomAsteroid asteroid) {
        toAdd.add(asteroid);
        world.addBody(asteroid);
    }

    private RandomAsteroid generateAsteroid(float scale) {
        RandomAsteroid asteroid;
        if (scale != 0)
            asteroid = new RandomAsteroid(scale);
        else
            asteroid = new RandomAsteroid();

        if (asteroid.getFarthestPointDistance() <= itemDropMaxRadius)
            asteroid.itemDrops.add(itemStrings[random.nextInt(itemStrings.length)]);
        return asteroid;
    }

    private void handleAsteroid(final RandomAsteroid asteroid) {
        if (asteroid.isRemoved())
            toRemove.add(asteroid);
        if (asteroid.getFarthestPointDistance() > itemDropMaxRadius
                && asteroid.getHealth() <= 0)
            addChildAsteroids(asteroid);
    }

    private void addChildAsteroids(final RandomAsteroid asteroid) {
        int amount = random.nextInt(2) + 1;
        for (int i = 0; i < amount; i++) {
            float maxScale = asteroid.getFarthestPointDistance();
            float minScale = 0.6f;
            float scaleRange = maxScale - minScale;
            float scale = random.nextFloat() * scaleRange + minScale;
            RandomAsteroid childAsteroid = generateAsteroid(scale);
            childAsteroid.setPosition(asteroid.getPosition());
            childAsteroid.scale(scale);
            childAsteroid.setVelocity(new Vector2(random.nextFloat() * 4 - 5,
                    random.nextFloat() * 6 - 3).scl(1 / scale));
            addAsteroid(childAsteroid);
        }
    }

    @Override
    protected void performAction() {
        if (getTicks() == 0) {
            addAsteroid(generateAsteroid(0));
        }
        for (RandomAsteroid asteroid : asteroids)
            handleAsteroid(asteroid);
        asteroids.removeAll(toRemove);
        toRemove.clear();
        asteroids.addAll(toAdd);
        toAdd.clear();
        if (asteroids.size() == 0)
            setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
