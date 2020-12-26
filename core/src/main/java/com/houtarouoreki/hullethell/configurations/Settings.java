package com.houtarouoreki.hullethell.configurations;

import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.bindables.BindableNumber;

public class Settings {
    public BindableNumber<Float> musicVolume
            = new BindableNumber<>(0.7f, 0f, 1f);
    public BindableNumber<Float> sfxVolume
            = new BindableNumber<>(0.7f, 0f, 1f);
    public Bindable<Boolean> backgrounds = new Bindable<>(true);
    public Bindable<Boolean> debugging = new Bindable<>(false);
    public Bindable<Boolean> renderFPS = new Bindable<>(false);
    public Bindable<Boolean> fullScreen = new Bindable<>(true);
    public Bindable<Boolean> healthBars = new Bindable<>(false);

    public void setSettings(SerializableSettings settings) {
        musicVolume.setValue(settings.musicVolume);
        sfxVolume.setValue(settings.sfxVolume);
        backgrounds.setValue(settings.backgrounds);
        debugging.setValue(settings.debugging);
        renderFPS.setValue(settings.renderFPS);
        fullScreen.setValue(settings.fullScreen);
        healthBars.setValue(settings.healthBars);
    }
}
