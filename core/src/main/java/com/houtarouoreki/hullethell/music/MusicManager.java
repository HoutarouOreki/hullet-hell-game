package com.houtarouoreki.hullethell.music;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import org.mini2Dx.core.graphics.Graphics;

public class MusicManager {
    private final AssetManager assetManager;
    private SongConfiguration currentSong;
    private SongNotification notification;

    public MusicManager(AssetManager am) {
        assetManager = am;
    }

    public void setCurrentSong(String fileName) {
        assetManager.get(fileName + ".mp3", Music.class).play();
        currentSong = assetManager.get(fileName + ".cfg");
        notification = new SongNotification(currentSong);
    }

    public void render(Graphics g) {
        if (notification == null) {
            return;
        }
        notification.render(g);
    }

    public void update(float delta) {
        if (notification == null) {
            return;
        }
        notification.update(delta);
        if (notification.isDone())
            notification = null;
    }
}
