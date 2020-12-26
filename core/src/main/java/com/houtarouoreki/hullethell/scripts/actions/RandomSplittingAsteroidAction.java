package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.entities.Item;
import com.houtarouoreki.hullethell.entities.RandomAsteroid;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSplittingAsteroidAction extends ScriptedAction {
    private final List<RandomAsteroid> asteroids = new ArrayList<>();
    private final List<RandomAsteroid> toAdd = new ArrayList<>();
    private final List<RandomAsteroid> toRemove = new ArrayList<>();
    private final float itemDropMaxRadius = 0.6f;
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
        asteroid.collisionBodyManager.disableCollisionsWith(asteroids);
        asteroid.collisionBodyManager.disableCollisionsWith(toAdd);
        toAdd.add(asteroid);
        world.addBody(asteroid);
    }

    private RandomAsteroid generateAsteroid(float scale) {
        RandomAsteroid asteroid;
        if (scale != 0)
            asteroid = new RandomAsteroid(scale);
        else
            asteroid = new RandomAsteroid();

        if (asteroid.collisionBodyManager.getFarthestPointDistance() <= itemDropMaxRadius) {
            Item itemDrop = getNewRandomItem();
            if (itemDrop != null)
                asteroid.items.add(itemDrop);
        }
        return asteroid;
    }

    private Item getNewRandomItem() {
        int randomNumber = random.nextInt(itemStrings.length);
        String itemName = itemStrings[randomNumber];
        if (itemName.equals("null"))
            return null;
        return new Item(itemName);
    }

    private void handleAsteroid(final RandomAsteroid asteroid) {
        if (asteroid.isRemoved())
            toRemove.add(asteroid);
        if (asteroid.collisionBodyManager.getFarthestPointDistance() > itemDropMaxRadius
                && !asteroid.isAlive())
            addChildAsteroids(asteroid);
    }

    private void addChildAsteroids(final RandomAsteroid asteroid) {
        int amount = random.nextInt(2) + 2;
        float lastAngle = 0;
        for (int i = 0; i < amount; i++) {
            float maxScale = asteroid.collisionBodyManager.getFarthestPointDistance() * 1.2f;
            float minScale = Math.max(itemDropMaxRadius,
                    asteroid.collisionBodyManager.getFarthestPointDistance() * 2 - 1.5f);
            float scaleRange = maxScale - minScale;
            float scale = random.nextFloat() * scaleRange + minScale;
            RandomAsteroid childAsteroid = generateAsteroid(scale);
            float speed = random.nextFloat() * 6 - 3;
            float angle = lastAngle + 70 + random.nextFloat() * 120;
            childAsteroid.setVelocity(new Vector2(-1, 0)
                    .rotated(angle, true)
                    .scl(speed)
                    .add(new Vector2(-2, 0))
                    .scl(1 / scale));
            childAsteroid.setPosition(asteroid.getPosition());
            childAsteroid.collisionBodyManager.disableCollisionsFor(0.1f);
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
