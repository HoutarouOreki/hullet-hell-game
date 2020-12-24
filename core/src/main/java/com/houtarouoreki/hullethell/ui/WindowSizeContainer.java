package com.houtarouoreki.hullethell.ui;

import com.houtarouoreki.hullethell.graphics.Drawable;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.graphics.Graphics;

public class WindowSizeContainer extends Drawable {
    private Vector2 screenSize = new Vector2();

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        screenSize = new Vector2(g.getWindowWidth(), g.getWindowHeight());
    }

    @Override
    public Vector2 getSize() {
        return screenSize;
    }
}
