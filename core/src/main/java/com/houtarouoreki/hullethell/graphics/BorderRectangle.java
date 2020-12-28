package com.houtarouoreki.hullethell.graphics;

import org.mini2Dx.core.graphics.Graphics;

public class BorderRectangle extends Drawable {
    public int thickness = 3;

    @Override
    public void draw(Graphics g) {
        g.setLineHeight(thickness);
        g.drawRect(getRenderPosition().x, getRenderPosition().y,
                getRenderSize().x, getRenderSize().y);
    }
}
