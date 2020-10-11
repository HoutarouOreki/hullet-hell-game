package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.PrimitiveBody;
import org.apache.commons.lang3.StringUtils;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.Viewport;

public abstract class BackgroundObject extends PrimitiveBody {
    private Vector2 size = new Vector2(0, 0);
    public String textureName;
    private Texture texture;

    public BackgroundObject(float initialX, float initialY) {
        this(new Vector2(initialX, initialY));
    }

    public BackgroundObject(Vector2 initialPos) {
        setPosition(initialPos);
        if (StringUtils.isNotEmpty(textureName)) {
            texture = new Texture(textureName);
        }
    }

    @Override
    public void render(Graphics g, Viewport vp, Vector2 viewArea) {
        if (texture != null) {
            g.drawTexture(texture, getPosition().x, getPosition().y, getSize().x, getSize().y);
        }
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }
}
