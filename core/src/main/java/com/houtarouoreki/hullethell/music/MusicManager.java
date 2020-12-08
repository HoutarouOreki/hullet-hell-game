package com.houtarouoreki.hullethell.music;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import org.mini2Dx.core.graphics.Graphics;

public class MusicManager {
    private final AssetManager assetManager;
    private final SongNotification notification;
    private SongConfiguration currentSong;

    public MusicManager(AssetManager am) {
        assetManager = am;
        notification = new SongNotification();
    }

    public void setCurrentSong(String fileName) {
        assetManager.get(fileName + ".mp3", Music.class).play();
        currentSong = assetManager.get(fileName + ".cfg");
    }

    public void render(Graphics g) {
        notification.render(g, currentSong);
    }

    public void update(float delta) {
        notification.update(delta);
    }
}
