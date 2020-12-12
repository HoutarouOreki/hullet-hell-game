package com.houtarouoreki.hullethell.configurations;

import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.bindables.BindableNumber;

public class Settings {
    public BindableNumber<Float> musicVolume
            = new BindableNumber<Float>(0.7f, 0f, 1f);
    public BindableNumber<Float> sfxVolume
            = new BindableNumber<Float>(0.7f, 0f, 1f);
    public Bindable<Boolean> backgrounds = new Bindable<Boolean>(true);
    public Bindable<Boolean> debugging = new Bindable<Boolean>(false);
    public Bindable<Boolean> renderFPS = new Bindable<Boolean>(false);
    public Bindable<Boolean> fullScreen = new Bindable<Boolean>(false);
}
