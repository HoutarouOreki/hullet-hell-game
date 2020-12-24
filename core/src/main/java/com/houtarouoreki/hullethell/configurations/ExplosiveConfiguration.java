package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExplosiveConfiguration extends BodyConfiguration {
    public final float explosionRadius;
    public final float explosionDamage;
    public final float explosionDuration;

    public ExplosiveConfiguration(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n")));
    }

    public ExplosiveConfiguration(List<String> configurationLines) {
        this(ConfigurationsHelper.getKeyValues(configurationLines));
    }

    public ExplosiveConfiguration(Map<String, String> keyValues) {
        super(keyValues);
        explosionRadius = Float.parseFloat(keyValues.get("explosionRadius"));
        explosionDamage = Float.parseFloat(keyValues.get("explosionDamage"));
        explosionDuration = Float.parseFloat(keyValues.get("explosionDuration"));
    }
}
