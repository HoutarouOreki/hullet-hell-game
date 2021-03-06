package com.houtarouoreki.hullethell;

import com.badlogic.gdx.graphics.Texture;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;
import com.houtarouoreki.hullethell.environment.Updatable;
import com.houtarouoreki.hullethell.graphics.BodySpriteManager;
import com.houtarouoreki.hullethell.graphics.Renderable;
import com.houtarouoreki.hullethell.graphics.SpriteInfo;
import com.houtarouoreki.hullethell.graphics.SpriteLayer;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public abstract class PrimitiveBody implements Renderable, Updatable {
    public final List<SpriteLayer> spritesLayers;
    public final BodySpriteManager bodySpriteManager = new BodySpriteManager(this);
    public BodyConfiguration configuration;
    public float rotation;
    public SpriteInfo spriteInfo;
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private Vector2 size = new Vector2();
    private double time = 0;
    private int ticks;

    public PrimitiveBody() {
        spritesLayers = new ArrayList<>();
        this.configuration = null;
    }

    public double getTime() {
        return time;
    }

    public void update(float delta) {
        setPosition(getPosition().add(getVelocity().scl(delta)));
        updateSpriteLayers();
        if (isSpriteRequired() && spriteInfo == null)
            spriteInfo = HulletHellGame.getAssetManager().get(configuration.path + "-sprite.hhc");
        time += delta;
        ticks++;
    }

    private void updateSpriteLayers() {
        for (SpriteLayer layer : spritesLayers) {
            layer.frameIndex.setValue((int) (time / layer.frameLength));
        }
    }

    public Vector2 getRenderPosition() {
        return RenderHelpers.translateToRenderPosition(getPosition());
    }

    public Vector2 getRenderSize() {
        return RenderHelpers.translateToRenderSize(getSize());
    }

    public void render(Graphics g) {
        for (SpriteLayer spriteLayer : spritesLayers) {
            renderSpriteLayer(g, spriteLayer);
        }
    }

    private void renderSpriteLayer(Graphics g, SpriteLayer layer) {
        if (layer.isEmpty())
            return;
        Vector2 renderSize = getRenderSize().scl(layer.scale);
        Vector2 renderPos = RenderHelpers.translateToRenderPosition(getPosition()
                .add(layer.offset));
        Vector2 topLeft = renderPos
                .add(renderSize.scl(-0.5f));
        Sprite sprite = layer.get(layer.frameIndex.getValue());
        sprite.setOriginCenter();
        sprite.setSize(renderSize.x, renderSize.y);
        sprite.setPosition(topLeft.x, topLeft.y);
        sprite.setRotation(rotation + layer.rotation);
        g.drawSprite(sprite);
    }

    public void addTexture(String textureName) {
        SpriteLayer newLayer = new SpriteLayer();
        spritesLayers.add(newLayer);
        newLayer.add(new Sprite(HulletHellGame.getAssetManager()
                .get(textureName, Texture.class)));
    }

    public void addAnimation(String textureName, int frames, float fps) {
        SpriteLayer newLayer = new SpriteLayer();
        spritesLayers.add(newLayer);
        for (int i = 0; i < frames; i++) {
            Sprite sprite = new Sprite(HulletHellGame
                    .getAssetManager().<Texture>get(textureName + "-" + i + ".png"));
            newLayer.add(sprite);
        }
        newLayer.frameLength = 1 / fps;
        newLayer.frameIndex.setMax(frames - 1);
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
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

    public int getTicks() {
        return ticks;
    }

    public boolean isSpriteRequired() {
        return false;
    }
}
