package com.houtarouoreki.hullethell.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import com.houtarouoreki.hullethell.numbers.LimitedNumber;

public class MusicManager {
    private final AssetManager assetManager;
    private final SongNotification notification;
    private final LimitedNumber<Float> volume;
    private Music currentSong;
    private float fadeOutLeft;
    private float fadeOutDuration;

    public MusicManager(AssetManager am, SongNotification notification) {
        assetManager = am;
        this.notification = notification;
        volume = new LimitedNumber<Float>(0.7f, 0f, 1f);
    }

    public void setCurrentSong(String fileName) {
        if (currentSong != null)
            currentSong.stop();
        currentSong = assetManager.get("music/" + fileName + ".mp3", Music.class);
        currentSong.setPosition(0);
        currentSong.setVolume(getVolume());
        SongConfiguration currentSongInfo = assetManager.get("music/" + fileName + ".cfg");
        notification.show(currentSongInfo);
        stopFadeOut();
    }

    private void stopFadeOut() {
        fadeOutLeft = -1;
        fadeOutDuration = 0;
        if (currentSong != null)
            currentSong.setVolume(getVolume());
    }

    public void update(float delta) {
        if (fadeOutDuration != 0 && currentSong != null) {
            applyFadeOut(delta);
        }
    }

    private void applyFadeOut(float delta) {
        float a = fadeOutLeft / fadeOutDuration;
        if (a <= 0) {
            currentSong.stop();
            currentSong.setVolume(volume.getValue());
            stopFadeOut();
            return;
        }
        currentSong.setVolume(a * getVolume());
        fadeOutLeft -= delta;
    }

    public void play() {
        currentSong.play();
        stopFadeOut();
    }

    public void pause() {
        currentSong.pause();
        stopFadeOut();
    }

    public void stop() {
        currentSong.stop();
        stopFadeOut();
    }

    public void fadeOut(float duration) {
        fadeOutLeft = fadeOutDuration = duration;
    }

    public float getVolume() {
        return volume.getValue();
    }

    public void setVolume(float volume) {
        this.volume.setValue(volume);
        currentSong.setVolume(getVolume());
    }

    public boolean isLooping() {
        return currentSong.isLooping();
    }

    public void setLooping(boolean looping) {
        currentSong.setLooping(looping);
    }
}
