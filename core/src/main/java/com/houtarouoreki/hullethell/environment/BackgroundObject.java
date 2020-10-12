package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.PrimitiveBody;
import org.apache.commons.lang3.StringUtils;

public abstract class BackgroundObject extends PrimitiveBody {
    public String textureName;

    public BackgroundObject(AssetManager assetManager, float initialX, float initialY) {
        this(assetManager, new Vector2(initialX, initialY));
    }

    public BackgroundObject(AssetManager assetManager, Vector2 initialPos) {
        super(assetManager);
        setPosition(initialPos);
    }

    @Override
    public void physics(float delta, Vector2 viewArea) {
        super.physics(delta, viewArea);
        if (sprite != null)
            sprite.rotate(2);
    }
}
