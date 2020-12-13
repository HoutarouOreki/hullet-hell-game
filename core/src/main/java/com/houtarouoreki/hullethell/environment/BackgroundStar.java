package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import org.mini2Dx.core.graphics.Graphics;

public class BackgroundStar extends BackgroundObject {
    public BackgroundStar(float initialX, float initialY, float size) {
        this(new Vector2(initialX, initialY), size);
    }

    public BackgroundStar(Vector2 initialPos, float size) {
        super(initialPos);
        setSize(new Vector2(size, size));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        Vector2 viewArea = World.viewArea;
        Vector2 vp = RenderHelpers.getViewport();
        g.fillRect(getPosition().x / viewArea.x * vp.x,
                getPosition().y / viewArea.y * vp.y,
                getSize().x / viewArea.x * vp.x,
                getSize().y / viewArea.y * vp.y);
    }

    public void update(float delta) {
        super.update(delta);
        if (getPosition().x + getSize().x < 0) {
            setPosition(new Vector2(World.viewArea.x, getPosition().y));
        }
    }
}
