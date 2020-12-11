package com.houtarouoreki.hullethell.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import com.houtarouoreki.hullethell.numbers.LimitedNumber;
import org.mini2Dx.core.graphics.Graphics;

public class MusicManager {
    private final AssetManager assetManager;
    private final SongNotification notification;
    private SongConfiguration currentSongInfo;
    private Music currentSong;
    private float fadeOutLeft;
    private float fadeOutDuration;
    private final LimitedNumber<Float> volume;

    public MusicManager(AssetManager am) {
        assetManager = am;
        notification = new SongNotification(am);
        volume = new LimitedNumber<Float>(0.7f, 0f, 1f);
    }

    public void setCurrentSong(String fileName) {
        currentSong = assetManager.get("music/" + fileName + ".mp3", Music.class);
        currentSong.setPosition(0);
        currentSong.setVolume(getVolume());
        currentSongInfo = assetManager.get("music/" + fileName + ".cfg");
        notification.show();
        stopFadeOut();
    }

    private void stopFadeOut() {
        fadeOutLeft = -1;
        fadeOutDuration = 0;
        if (currentSong != null)
            currentSong.setVolume(getVolume());
    }

    public void render(Graphics g) {
        notification.render(g, currentSongInfo);
    }

    public void update(float delta) {
        notification.update(delta);
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

    public void setLooping(boolean looping) {
        currentSong.setLooping(looping);
    }

    public boolean isLooping() {
        return currentSong.isLooping();
    }
}
