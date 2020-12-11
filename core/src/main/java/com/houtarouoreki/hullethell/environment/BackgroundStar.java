package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.Viewport;

public class BackgroundStar extends BackgroundObject {
    public BackgroundStar(float initialX, float initialY, float size) {
        this(new Vector2(initialX, initialY), size);
    }

    public BackgroundStar(Vector2 initialPos, float size) {
        super(initialPos);
        setSize(new Vector2(size, size));
    }

    @Override
    public void render(Graphics g, Viewport vp, Vector2 viewArea) {
        g.setColor(Color.GRAY);
        g.fillRect(getPosition().x / viewArea.x * vp.getWidth(),
                getPosition().y / viewArea.y * vp.getHeight(),
                getSize().x / viewArea.x * vp.getWidth(),
                getSize().y / viewArea.y * vp.getHeight());
    }

    @Override
    public void physics(float delta, Vector2 viewArea) {
        super.physics(delta, viewArea);
        if (getPosition().x + getSize().x < 0) {
            setPosition(new Vector2(viewArea.x, getPosition().y));
        }
    }
}
