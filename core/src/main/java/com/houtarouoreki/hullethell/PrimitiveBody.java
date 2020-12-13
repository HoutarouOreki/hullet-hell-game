package com.houtarouoreki.hullethell;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.environment.Updatable;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.Renderable;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import com.houtarouoreki.hullethell.numbers.LoopInt;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public abstract class PrimitiveBody implements Renderable, Updatable {
    protected final List<Sprite> sprites;
    protected final LoopInt frameIndex;
    private final Vector2 position = new Vector2();
    private final Vector2 velocity = new Vector2();
    private final Vector2 size = new Vector2();
    protected float frameLength;
    private String textureName;
    private double time = 0;

    public PrimitiveBody() {
        sprites = new ArrayList<Sprite>();
        frameIndex = new LoopInt(0, 0);
    }

    public double getTime() {
        return time;
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public void update(float delta) {
        setPosition(getPosition().add(new Vector2(getVelocity()).scl(delta)));
        time += delta;
        frameIndex.setValue((int) (time / frameLength));
    }

    public Vector2 getRenderPosition() {
        return RenderHelpers.translateToRenderPosition(position);
    }

    public Vector2 getRenderSize() {
        return new Vector2(size).scl(RenderHelpers.getViewport())
                .scl(1 / World.viewArea.x, 1 / World.viewArea.y);
    }

    public void render(Graphics g) {
        if (sprites.size() == 0)
            return;
        Vector2 renderSize = getRenderSize();
        Vector2 topLeft = new Vector2(getRenderPosition())
                .add(new Vector2(renderSize).scl(-0.5f));
        Sprite sprite = sprites.get(frameIndex.getValue());
        sprite.setOriginCenter();
        sprite.setSize(renderSize.x, renderSize.y);
        sprite.setPosition(topLeft.x, topLeft.y);
        g.drawSprite(sprite);
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
        sprites.clear();
        sprites.add(new Sprite(HulletHellGame.getAssetManager()
                .get(textureName, Texture.class)));
        frameIndex.setMax(0);
    }

    public void setAnimation(String textureName, int frames, float fps) {
        sprites.clear();
        for (int i = 0; i < frames; i++) {
            Sprite sprite = new Sprite(HulletHellGame
                    .getAssetManager().<Texture>get(textureName + "-" + i + ".png"));
            sprites.add(sprite);
        }
        frameLength = 1 / fps;
        frameIndex.setMax(frames - 1);
    }

    public Vector2 getSize() {
        return size.cpy();
    }

    public void setSize(Vector2 size) {
        this.size.set(size);
    }
}
