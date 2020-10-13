package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShipConfiguration {
    String name;
    int maxHealth;
    Vector2 size;
    List<CollisionCircle> collisionCircles;

    public ShipConfiguration(List<String> configurationLines) {
        Map<String, String> keyValues = ConfigurationKeyValuesReader.getKeyValues(configurationLines);
        name = keyValues.get("name");
        maxHealth = Integer.parseInt(keyValues.get("maxHealth"));
        size = parseVector2(keyValues.get("size"));
        collisionCircles = parseCollisionCircles(keyValues.get("collisionCircles"));
    }

    private Vector2 parseVector2(String t) {
        String[] xy = t.split(", ");
        return new Vector2(Float.parseFloat(xy[0]), Float.parseFloat(xy[1]));
    }

    private List<CollisionCircle> parseCollisionCircles(String t) {
        String[] textCollisionCircles = t.split(" / ");
        List<CollisionCircle> collisionCircles = new ArrayList<CollisionCircle>();
        for (String textCollisionCircle : textCollisionCircles) {
            collisionCircles.add(parseCollisionCircle(textCollisionCircle));
        }
        return collisionCircles;
    }

    private CollisionCircle parseCollisionCircle(String t) {
        String[] xyr = t.split(", ");
        return new CollisionCircle(
                Float.parseFloat(xyr[0]),
                Float.parseFloat(xyr[1]),
                Float.parseFloat(xyr[2]));
    }
}
