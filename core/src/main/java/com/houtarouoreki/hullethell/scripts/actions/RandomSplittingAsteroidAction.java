package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.entities.Item;
import com.houtarouoreki.hullethell.entities.RandomAsteroid;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionMatcherArg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

public class RandomSplittingAsteroidAction extends ScriptedAction {
    private final List<RandomAsteroid> asteroids = new ArrayList<>();
    private final List<RandomAsteroid> toAdd = new ArrayList<>();
    private final List<RandomAsteroid> toRemove = new ArrayList<>();
    private final float itemDropMaxRadius = 0.5f;
    private final Random random = new Random();
    private final List<String> itemStrings = new LinkedList<>();

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
        int randomNumber = random.nextInt(itemStrings.size());
        String itemName = itemStrings.get(randomNumber);
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

    @Override
    protected void addArgumentsInfo() {
        parser.matcherArgs.add(new ActionMatcherArg(
                "Items",
                "What items the asteroid may drop." +
                        " One item will be chosen out of all at random." +
                        " Use \"null\" for no item. The example has " +
                " 3/8 chance for copperOre, 2/8 chance for ironOre, " +
                " and 3/8 chance for no item drop.",
                "3 copperOre, 2 ironOre, 3 null",
                item_amount_pattern,
                this::addItemDrop,
                true
        ));
    }

    private void addItemDrop(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("itemAmount"));
        String itemName = matcher.group("itemName");
        for (int i = 0; i < amount; i++)
            itemStrings.add(itemName);
    }
}
