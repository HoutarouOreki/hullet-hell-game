package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ConfigurationsHelper;

import java.util.*;

public class SpriteInfo {
    private static final String extension = ".png";
    public final EnumSet<Size> availableSizes = EnumSet.noneOf(Size.class);
    public String textureFolder;
    public String textureName;
    public final HashMap<Size, Integer> damageTexturesCount = new HashMap<>();
    public final HashMap<Size, Integer> framesCount = new HashMap<>();

    public SpriteInfo() {
    }

    public SpriteInfo(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n")));
    }

    public SpriteInfo(List<String> configurationLines) {
        this(ConfigurationsHelper.getKeyValues(configurationLines));
    }

    public SpriteInfo(Map<String, String> keyValues) {
        framesCount.put(Size.DEFAULT, Integer.parseInt(keyValues
                .getOrDefault("framesCount", "0")));
        damageTexturesCount.put(Size.DEFAULT, Integer.parseInt(keyValues
                .getOrDefault("damageTexturesCount", "0")));
        textureFolder = keyValues.get("folder");
        textureName = keyValues.get("name");
        if (keyValues.containsKey("availableSizes")) {
            for (String sizeString : keyValues.get("availableSizes").split(", ")) {
                Size size = Size.fromString(sizeString);
                availableSizes.add(size);
                framesCount.put(size, Integer.parseInt(keyValues
                        .getOrDefault("framesCount-" + sizeString,
                                framesCount.get(Size.DEFAULT).toString())));
                damageTexturesCount.put(size, Integer.parseInt(keyValues
                        .getOrDefault("damageTexturesCount-" + sizeString,
                                damageTexturesCount.get(Size.DEFAULT).toString())));
            }
        } else {
            availableSizes.add(Size.DEFAULT);
        }
    }



    public String getPathWithoutExtension() {
        return textureFolder + '/' + textureName;
    }

    public String getPath() {
        return getPathWithoutExtension() + extension;
    }

    public String getPathWithoutExtension(Size size) {
        return getPathWithoutExtension() + getSizeString(size);
    }

    public String getPath(Size size) {
        return getPathWithoutExtension(size) + extension;
    }

    public String getDamagedPathWithoutExtension(int damageFrame) {
        if (damageFrame == 0)
            return getPathWithoutExtension();
        return getPathWithoutExtension() + getDamageString(damageFrame);
    }

    public String getDamagedPath(int damageFrame) {
        return getDamagedPathWithoutExtension(damageFrame) + extension;
    }

    public String getDamagedPathWithoutExtension(Size size, int damageFrame) {
        if (damageFrame == 0)
            return getPathWithoutExtension(size);
        return getPathWithoutExtension(size) + getDamageString(damageFrame);
    }

    public String getDamagedPath(Size size, int damageFrame) {
        return getDamagedPathWithoutExtension(size, damageFrame) + extension;
    }

    public String getPathWithoutExtension(int animationFrame) {
        return getPathWithoutExtension() + "-" + animationFrame;
    }

    public String getPath(int animationFrame) {
        return getPathWithoutExtension(animationFrame) + extension;
    }

    public String getPathWithoutExtension(Size size, int animationFrame) {
        return getPathWithoutExtension(size) + "-" + animationFrame;
    }

    public String getPath(Size size, int animationFrame) {
        return getPathWithoutExtension(size, animationFrame) + extension;
    }

    public String getSizeString(Size size) {
        if (size == null || !availableSizes.contains(size))
            return "";
        switch (size) {
            case TINY:
                return "-tiny";
            case SMALL:
                return "-small";
            case MEDIUM:
                return "-medium";
            case LARGE:
                return "-large";
            default:
                return "";
        }
    }

    public String getDamageString(int damageFrame) {
        return "-dmg" + damageFrame;
    }

    public Texture getTexture(Size size, int damageFrame) {
        String path = getDamagedPath(size, damageFrame);
        return HulletHellGame.getAssetManager().get(path);
    }

    public enum Size {
        DEFAULT,
        TINY,
        SMALL,
        MEDIUM,
        LARGE;

        public static Size fromString(String s) {
            switch (s.toLowerCase()) {
                case "tiny":
                    return TINY;
                case "small":
                    return SMALL;
                case "medium":
                    return MEDIUM;
                case "large":
                    return LARGE;
                default:
                    return DEFAULT;
            }
        }
    }
}
