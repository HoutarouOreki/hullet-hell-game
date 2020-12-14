package com.houtarouoreki.hullethell.configurations;

import org.mini2Dx.core.serialization.annotation.Field;

public class SerializableSettings {
    @Field
    public Float musicVolume;
    @Field
    public Float sfxVolume;
    @Field
    public Boolean backgrounds;
    @Field
    public Boolean debugging;
    @Field
    public Boolean renderFPS;
    @Field
    public Boolean fullScreen;

    public SerializableSettings() {
        musicVolume = 0.7f;
        sfxVolume = 0.7f;
        backgrounds = true;
        debugging = false;
        renderFPS = false;
        fullScreen = true;
    }

    public SerializableSettings(Settings settings) {
        musicVolume = settings.musicVolume.getValue();
        sfxVolume = settings.sfxVolume.getValue();
        backgrounds = settings.backgrounds.getValue();
        debugging = settings.debugging.getValue();
        renderFPS = settings.renderFPS.getValue();
        fullScreen = settings.fullScreen.getValue();
    }
}
