package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.collisions.Circle;
import com.houtarouoreki.hullethell.numbers.Vector2;

import javax.management.RuntimeErrorException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ConfigurationsHelper {
    public static Map<String, String> getKeyValues(List<String> lines) {
        Map<String, String> returnPairs = new HashMap<>();
        for (String line : lines) {
            if (line.isEmpty())
                continue;
            String[] strings = line.split(": ");
            returnPairs.put(strings[0], strings[1]);
        }
        return returnPairs;
    }

    public static List<String> getLinesFromFile(AssetManager am, String filePath) {
        File file = am.get(filePath);
        List<String> lines = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file " + filePath + " when loading a configuration");
            e.printStackTrace();
            throw new RuntimeErrorException(new Error("Could not find file " + filePath + " when loading a configuration"));
        }
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }

    public static Vector2 parseVector2(String t) {
        String[] xy = t.split(", ");
        return new Vector2(Float.parseFloat(xy[0]), Float.parseFloat(xy[1]));
    }

    public static List<Circle> parseCircles(String t) {
        String[] circleStrings = t.split(" / ");
        List<Circle> collisionCircles = new ArrayList<>(circleStrings.length);
        for (String circleString : circleStrings) {
            collisionCircles.add(parseCircle(circleString));
        }
        return collisionCircles;
    }

    public static Circle parseCircle(String t) {
        String[] xyr = t.split(", ");
        return new Circle(new Vector2(Float.parseFloat(xyr[0]), Float.parseFloat(xyr[1])),
                Float.parseFloat(xyr[2]));
    }
}
