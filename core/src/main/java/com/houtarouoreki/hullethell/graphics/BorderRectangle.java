package com.houtarouoreki.hullethell.graphics;

import org.mini2Dx.core.graphics.Graphics;

public class BorderRectangle extends Drawable {
    @Override
    public void draw(Graphics g) {
        g.setLineHeight(3);
        g.drawRect(getRenderPosition().x, getRenderPosition().y,
                getRenderSize().x, getRenderSize().y);
    }
}