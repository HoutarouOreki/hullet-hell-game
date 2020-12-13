package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.PrimitiveBody;

public abstract class BackgroundObject extends PrimitiveBody {

    public BackgroundObject(Vector2 initialPos) {
        setPosition(initialPos);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (sprite != null)
            sprite.rotate(2);
    }
}
