package com.houtarouoreki.hullethell.graphics;

import org.mini2Dx.core.graphics.Graphics;

public class Rectangle extends Drawable {
    @Override
    public void onUpdate(float delta) {
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect(getRenderPosition().x, getRenderPosition().y,
                getRenderSize().x, getRenderSize().y);
    }
}
