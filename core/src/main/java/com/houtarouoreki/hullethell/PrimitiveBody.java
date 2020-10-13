package com.houtarouoreki.hullethell;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.Sprite;
import org.mini2Dx.core.graphics.viewport.Viewport;

public abstract class PrimitiveBody {
    public AssetManager assetManager;
    protected Sprite sprite;
    private String textureName;

    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private Vector2 size = new Vector2();

    public PrimitiveBody(AssetManager assetManager) {
        this.assetManager = assetManager;
        sprite = new Sprite();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void physics(float delta, Vector2 viewArea) {
        getPosition().add(new Vector2(getVelocity()).scl(delta));
    }

    public Vector2 getRenderPosition(Viewport vp, Vector2 viewArea) {
        return RenderHelpers.translateToRenderPosition(position, vp, viewArea);
    }

    public Vector2 getRenderSize(Viewport vp, Vector2 viewArea) {
        return new Vector2(size).scl(vp.getWidth(), vp.getHeight()).scl(1 / viewArea.x, 1 / viewArea.y);
    }

    public void render(Graphics g, Viewport vp, Vector2 viewArea) {
        if (sprite.getTexture() != null) {
            Vector2 renderSize = getRenderSize(vp, viewArea);
            Vector2 topLeft = new Vector2(getRenderPosition(vp, viewArea)).add(new Vector2(renderSize).scl(-0.5f));
            sprite.setOriginCenter();
            sprite.setSize(renderSize.x, renderSize.y);
            sprite.setPosition(topLeft.x, topLeft.y);
            g.drawSprite(sprite);
        }
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
        sprite = new Sprite(assetManager.get(textureName, Texture.class));
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }
}
