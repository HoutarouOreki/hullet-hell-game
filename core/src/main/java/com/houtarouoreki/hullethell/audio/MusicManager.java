package com.houtarouoreki.hullethell.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.bindables.BindableNumber;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;

public class MusicManager {
    public final BindableNumber<Float> volume;
    private final AssetManager assetManager;
    private final SongNotification notification;
    private Music currentSong;
    private float fadeOutLeft;
    private float fadeOutDuration;
    private SongConfiguration currentSongInfo;
    private float fadeInLeft;
    private float fadeInDuration;

    public MusicManager(AssetManager am, SongNotification notification) {
        assetManager = am;
        this.notification = notification;
        volume = new BindableNumber<>(0.7f, 0f, 1f);
        volume.addListener((oldValue, newValue) -> {
            if (currentSong != null)
                currentSong.setVolume(newValue);
        });
        volume.bindTo(HulletHellGame.getSettings().musicVolume);
    }

    public SongConfiguration getCurrentSongInfo() {
        return currentSongInfo;
    }

    public void setCurrentSong(String fileName) {
        if (currentSong != null)
            currentSong.stop();
        currentSong = assetManager.get("music/" + fileName + ".mp3", Music.class);
        currentSong.setPosition(0);
        currentSong.setVolume(getVolume());
        currentSongInfo = assetManager.get("music/" + fileName + ".cfg");
        notification.show(currentSongInfo);
        stopTransitions();
    }

    private void stopTransitions() {
        fadeOutLeft = fadeInLeft = -1;
        fadeOutDuration = fadeInDuration = 0;
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
            stopTransitions();
            return;
        }
        currentSong.setVolume(a * getVolume());
        fadeOutLeft -= delta;
    }

    private void applyFadeIn(float delta) {
        float a = 1 - (fadeInLeft / fadeInDuration);
        if (a >= 1) {
            currentSong.setVolume(volume.getValue());
            stopTransitions();
            return;
        }
        currentSong.setVolume(a * getVolume());
        fadeInLeft -= delta;
    }

    public void play() {
        currentSong.play();
        stopTransitions();
    }

    public void pause() {
        currentSong.pause();
        stopTransitions();
    }

    public void stop() {
        currentSong.stop();
        stopTransitions();
    }

    public void fadeOut(float duration) {
        fadeOutLeft = fadeOutDuration = duration;
    }

    public void fadeIn(float duration) {
        fadeInLeft = fadeInDuration = duration;
    }

    public float getVolume() {
        return volume.getValue();
    }

    public void setVolume(float volume) {
        this.volume.setValue(volume);
    }

    public boolean isLooping() {
        return currentSong.isLooping();
    }

    public void setLooping(boolean looping) {
        currentSong.setLooping(looping);
    }
}
