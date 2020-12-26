package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;
import com.houtarouoreki.hullethell.HulletHellGame;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ShipConfiguration extends BodyConfiguration {
    public final float maxSpeed;
    public final float cannonTimeOut;
    public final float sprintTimeOut;
    public final String ammunitionType;
    public final String ammunitionName;
    public final float ammunitionSpeed;

    public ShipConfiguration(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n")),
                file.pathWithoutExtension());
    }

    public ShipConfiguration(List<String> configurationLines, String path) {
        this(ConfigurationsHelper.getKeyValues(configurationLines), path);
    }

    public ShipConfiguration(Map<String, String> keyValues, String path) {
        super(keyValues, path);
        maxSpeed = Float.parseFloat(keyValues.getOrDefault("maxSpeed", "0"));
        cannonTimeOut = Float.parseFloat(keyValues.getOrDefault("cannonTimeOut", "0"));
        sprintTimeOut = Float.parseFloat(keyValues.getOrDefault("sprintTimeOut", "0"));
        ammunitionType = keyValues.getOrDefault("ammunitionType", "bullets");
        ammunitionName = keyValues.get("ammunitionName");
        ammunitionSpeed = Float.parseFloat(keyValues.getOrDefault("ammunitionSpeed", "0"));
    }

    public static ShipConfiguration fromName(String name) {
        return HulletHellGame.getAssetManager().get("ships/" + name + ".cfg");
    }
}
