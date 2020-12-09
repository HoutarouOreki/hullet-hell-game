package com.houtarouoreki.hullethell.music;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import org.mini2Dx.core.graphics.Graphics;

public class MusicManager {
    private final AssetManager assetManager;
    private final SongNotification notification;
    private SongConfiguration currentSongInfo;
    private Music currentSong;

    public MusicManager(AssetManager am) {
        assetManager = am;
        notification = new SongNotification();
    }

    public void setCurrentSong(String fileName) {
        currentSong = assetManager.get(fileName + ".mp3", Music.class);
        currentSongInfo = assetManager.get(fileName + ".cfg");
        notification.show();
    }

    public void render(Graphics g) {
        notification.render(g, currentSongInfo);
    }

    public void update(float delta) {
        notification.update(delta);
    }

    public void play() {
        currentSong.play();
    }

    public void pause() {
        currentSong.pause();
    }

    public void stop() {
        currentSong.stop();
    }
}
