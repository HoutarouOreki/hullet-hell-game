package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BodyConfiguration {
    public final String name;
    public final int maxHealth;
    public final Vector2 size;
    public final List<CollisionCircle> collisionCircles;

    public BodyConfiguration(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n")));
    }

    public BodyConfiguration(List<String> configurationLines) {
        this(ConfigurationsHelper.getKeyValues(configurationLines));
    }

    public BodyConfiguration(Map<String, String> keyValues) {
        name = keyValues.get("name");
        maxHealth = Integer.parseInt(keyValues.get("maxHealth"));
        size = ConfigurationsHelper.parseVector2(keyValues.get("size"));
        collisionCircles = ConfigurationsHelper.parseCollisionCircles(keyValues.get("collisionCircles"));
    }
}
