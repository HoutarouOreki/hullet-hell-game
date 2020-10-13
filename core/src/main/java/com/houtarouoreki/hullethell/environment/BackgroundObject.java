package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.PrimitiveBody;

public abstract class BackgroundObject extends PrimitiveBody {

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
