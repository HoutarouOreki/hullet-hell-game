package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.Circle;
import com.houtarouoreki.hullethell.numbers.Vector2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BodyConfiguration {
    public final String name;
    public final int maxHealth;
    public final Vector2 size;
    public final List<Circle> collisionCircles;
    public final String path;
    public final String fileName;
    public final int damage;

    public BodyConfiguration(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n")),
                file.pathWithoutExtension());
    }

    public BodyConfiguration(List<String> configurationLines, String path) {
        this(ConfigurationsHelper.getKeyValues(configurationLines), path);
    }

    public BodyConfiguration(Map<String, String> keyValues, String path) {
        this.path = path;
        System.out.println("path = " + path);
        fileName = path.substring(path.lastIndexOf('/') + 1);
        if (keyValues.containsKey("name"))
            name = keyValues.get("name");
        else {
            int lastFSlashIndex = path.lastIndexOf('/');
            name = path.substring(lastFSlashIndex + 1);
        }
        maxHealth = Integer.parseInt(keyValues.get("maxHealth"));
        size = ConfigurationsHelper.parseVector2(keyValues.get("size"));
        collisionCircles = ConfigurationsHelper.parseCircles(keyValues.get("collisionCircles"));
        damage = Integer.parseInt(keyValues.getOrDefault("damage", "0"));
    }

    public static BodyConfiguration fromPath(String path) {
        if (!path.endsWith(".hhc"))
            path += ".hhc";
        return HulletHellGame.getAssetManager().get(path);
    }
}
