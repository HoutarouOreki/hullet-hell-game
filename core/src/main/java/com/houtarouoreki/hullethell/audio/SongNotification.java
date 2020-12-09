package com.houtarouoreki.hullethell.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import org.mini2Dx.core.graphics.Graphics;

public class SongNotification {
    private final float length = 5;
    private final AssetManager assetManager;
    private Texture texture;
    private float timeLeft = length;

    public SongNotification(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void show() {
        timeLeft = length;
    }

    public void render(Graphics g, SongConfiguration song) {
        if (song == null || timeLeft < 0 || texture == null)
            return;

        float right = 1280;
        float height = 80;
        float bottom = 720;
        float top = bottom - height;
        float fadeLength = 1;
        float width = 300;

        float nonFadeLength = length - fadeLength;
        if (timeLeft > length - fadeLength) {
            float a = 1 - ((timeLeft - nonFadeLength) / fadeLength);
            right += Interpolation.sineIn.apply(width + 30, 0, a);
        } else if (timeLeft < fadeLength) {
            float a = 1 - (timeLeft / fadeLength);
            right += Interpolation.sineIn.apply(0, width + 30, a);
        }
        float left = right - width;
        g.setColor(Color.SKY);
        g.drawTexture(texture, left, top, width, height);
        g.setColor(new Color(0, 0, 143 / 255f, 1));
        g.drawString(song.title, left + 90, top + 24);
        //g.setColor(new Color(15 / 255f, 15 / 255f, 96 / 255f, 1));
        g.drawString("by " + song.author, left + 90, top + 42);
    }

    public void update(float delta) {
        timeLeft -= delta;
        if (texture == null && assetManager.isLoaded("ui/songNotification.png"))
            texture = assetManager.get("ui/songNotification.png");
    }
}
