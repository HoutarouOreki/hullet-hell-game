package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BodyConfiguration {
    private final String name;
    private final int maxHealth;
    private final Vector2 size;
    private final List<CollisionCircle> collisionCircles;

    public BodyConfiguration(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n").clone()));
    }

    public BodyConfiguration(List<String> configurationLines) {
        Map<String, String> keyValues = ConfigurationsHelper.getKeyValues(configurationLines);
        name = keyValues.get("name");
        maxHealth = Integer.parseInt(keyValues.get("maxHealth"));
        size = ConfigurationsHelper.parseVector2(keyValues.get("size"));
        collisionCircles = ConfigurationsHelper.parseCollisionCircles(keyValues.get("collisionCircles"));
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Vector2 getSize() {
        return size;
    }

    public List<CollisionCircle> getCollisionCircles() {
        return collisionCircles;
    }
}
