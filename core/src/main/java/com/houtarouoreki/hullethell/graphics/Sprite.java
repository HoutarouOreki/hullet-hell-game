package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.graphics.Graphics;

public class Sprite extends Drawable {
    public Texture texture;

    @Override
    public void draw(Graphics g) {
        if (texture == null)
            return;
        g.drawTexture(texture, getRenderPosition().x, getRenderPosition().y,
                getRenderSize().x, getRenderSize().y);
    }
}
