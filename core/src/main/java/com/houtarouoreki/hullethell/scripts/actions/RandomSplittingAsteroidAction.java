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
    private final float itemDropMaxRadius = 0.5f;
    private Random random;
    private String[] itemStrings;

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        if (arguments.isEmpty())
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

        if (asteroid.getCollisionBodyManager().getFarthestPointDistance() <= itemDropMaxRadius) {
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
        if (asteroid.getCollisionBodyManager().getFarthestPointDistance() > itemDropMaxRadius
                && !asteroid.isAlive())
            addChildAsteroids(asteroid);
    }

    private void addChildAsteroids(final RandomAsteroid asteroid) {
        int amount = random.nextInt(2) + 2;
        float lastAngle = random.nextFloat() * 360;
        for (int i = 0; i < amount; i++) {
            float maxScale = asteroid.getCollisionBodyManager().getFarthestPointDistance() * 1.2f;
            float minScale = Math.max(itemDropMaxRadius,
                    asteroid.getCollisionBodyManager().getFarthestPointDistance() * 2 - 1.5f);
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
            addAsteroid(childAsteroid);
            childAsteroid.parent = asteroid;
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
        if (asteroids.isEmpty())
            setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
