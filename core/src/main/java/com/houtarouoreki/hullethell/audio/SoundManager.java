package com.houtarouoreki.hullethell.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.bindables.BindableNumber;

public class SoundManager {
    public final BindableNumber<Float> volume;
    private final AssetManager assetManager;

    public SoundManager(AssetManager am) {
        assetManager = am;
        volume = new BindableNumber<Float>(0.7f, 0f, 1f);
        volume.bindTo(HulletHellGame.getSettings().sfxVolume);
    }

    public float getVolume() {
        return volume.getValue();
    }

    public void setVolume(float volume) {
        this.volume.setValue(volume);
    }

    public long playSound(String soundName) {
        return assetManager.get("sounds/" + soundName + ".mp3", Sound.class).play(getVolume());
    }

    public long playSound(String soundName, float volume) {
        return assetManager.get("sounds/" + soundName + ".mp3", Sound.class)
                .play(getVolume() * volume);
    }
}
