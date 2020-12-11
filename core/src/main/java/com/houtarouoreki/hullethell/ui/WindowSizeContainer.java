package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.graphics.Drawable;
import org.mini2Dx.core.graphics.Graphics;

public class WindowSizeContainer extends Drawable {
    private final Vector2 screenSize = new Vector2();

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        screenSize.set(g.getWindowWidth(), g.getWindowHeight());
    }

    @Override
    public Vector2 getSize() {
        return screenSize.cpy();
    }
}
