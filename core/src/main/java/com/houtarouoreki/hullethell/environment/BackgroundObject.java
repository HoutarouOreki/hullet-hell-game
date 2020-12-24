package com.houtarouoreki.hullethell.environment;

import com.houtarouoreki.hullethell.PrimitiveBody;
import com.houtarouoreki.hullethell.numbers.Vector2;

public abstract class BackgroundObject extends PrimitiveBody {

    public BackgroundObject(Vector2 initialPos) {
        setPosition(initialPos);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (spritesLayers.isEmpty())
            return;
        spritesLayers.get(0).rotation += delta * 100;
    }
}
