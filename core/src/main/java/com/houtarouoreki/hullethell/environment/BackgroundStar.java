package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.graphics.Color;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.graphics.Graphics;

public class BackgroundStar extends BackgroundObject {
    public BackgroundStar(float initialX, float initialY, float size) {
        this(new Vector2(initialX, initialY), size);
    }

    public BackgroundStar(Vector2 initialPos, float size) {
        super(initialPos);
        this.setSize(new Vector2(size));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        Vector2 viewArea = World.viewArea;
        Vector2 vp = RenderHelpers.getViewport();
        Vector2 topLeft = getPosition().div(viewArea).scl(vp);
        Vector2 rectSize = getSize().div(viewArea).scl(vp);
        g.fillRect(topLeft.x, topLeft.y, rectSize.x, rectSize.y);
    }

    public void update(float delta) {
        super.update(delta);
        if (getPosition().x + getSize().x < 0)
            setPosition(new Vector2(World.viewArea.x, getPosition().y));
    }
}
