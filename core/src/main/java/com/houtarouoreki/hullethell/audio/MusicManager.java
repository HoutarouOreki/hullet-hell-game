package com.houtarouoreki.hullethell.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import org.mini2Dx.core.graphics.Graphics;

public class MusicManager {
    private final AssetManager assetManager;
    private final SongNotification notification;
    private SongConfiguration currentSongInfo;
    private Music currentSong;
    private float fadeOutLeft;
    private float fadeOutDuration;
    private float volume = 1;

    public MusicManager(AssetManager am) {
        assetManager = am;
        notification = new SongNotification(am);
    }

    public void setCurrentSong(String fileName) {
        currentSong = assetManager.get("music/" + fileName + ".mp3", Music.class);
        currentSong.setPosition(0);
        currentSong.setVolume(volume);
        currentSongInfo = assetManager.get("music/" + fileName + ".cfg");
        notification.show();
        stopFadeOut();
    }

    private void stopFadeOut() {
        fadeOutLeft = -1;
        fadeOutDuration = 0;
        if (currentSong != null)
            currentSong.setVolume(volume);
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
            currentSong.setVolume(volume);
            stopFadeOut();
            return;
        }
        currentSong.setVolume(a * volume);
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
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0, Math.min(1, volume));
        currentSong.setVolume(getVolume());
    }
}
