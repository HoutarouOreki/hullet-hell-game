package com.houtarouoreki.hullethell.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private final AssetManager assetManager;
    private float volume = 1;

    public SoundManager(AssetManager am) {
        assetManager = am;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = Math.min(1, Math.max(0, volume));
    }

    public long playSound(String soundName) {
        return assetManager.get("sounds/" + soundName + ".mp3", Sound.class).play(volume);
    }

    public long playSound(String soundName, float volume) {
        return assetManager.get("sounds/" + soundName + ".mp3", Sound.class)
                .play(getVolume() * volume);
    }
}
