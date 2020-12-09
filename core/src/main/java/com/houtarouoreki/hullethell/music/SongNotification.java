package com.houtarouoreki.hullethell.music;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import org.mini2Dx.core.graphics.Graphics;

public class SongNotification {
    private final float length = 5;
    private float timeLeft = length;

    public void show() {
        timeLeft = length;
    }

    public void render(Graphics g, SongConfiguration song) {
        if (song == null || timeLeft < 0)
            return;
        float right = g.getViewportWidth();
        float height = 60;
        float top = g.getViewportHeight() - height;
        float bottom = g.getViewportHeight();
        float fadeLength = 1;
        float width = 300;
        if (timeLeft > length - fadeLength) {
            right += width * Interpolation.sineIn.apply(timeLeft, length - fadeLength, length);
        } else if (timeLeft < fadeLength) {
            right += width * Interpolation.sineIn.apply(timeLeft, fadeLength, 0);
        }
        float left = right - width;
        g.fillRect(left, top, right, bottom);
        float margin = 20;
        g.drawString(song.toString(),left + margin, top + margin);
    }

    public void update(float delta) {
        timeLeft -= delta;
    }
}
