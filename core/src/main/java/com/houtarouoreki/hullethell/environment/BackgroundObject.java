package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.PrimitiveBody;
import org.mini2Dx.core.graphics.Sprite;

public abstract class BackgroundObject extends PrimitiveBody {

    public BackgroundObject(Vector2 initialPos) {
        setPosition(initialPos);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        for (Sprite sprite : sprites) {
            sprite.rotate(2);
        }
    }
}
