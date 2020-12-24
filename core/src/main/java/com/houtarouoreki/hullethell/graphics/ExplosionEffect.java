package com.houtarouoreki.hullethell.graphics;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.PrimitiveBody;
import com.houtarouoreki.hullethell.environment.Finishable;
import com.houtarouoreki.hullethell.numbers.Vector2;

public class ExplosionEffect extends PrimitiveBody implements Finishable {
    private boolean done;

    public ExplosionEffect(Vector2 position) {
        addAnimation("effects/Explosion", 6, 5);
        spritesLayers.get(0).frameIndex.addListener((oldValue, newValue) -> {
            if (oldValue == 5 && newValue == 0) {
                setDone();
            }
        });
        this.setPosition(position);
        setSize(new Vector2(5, 5));
        HulletHellGame.getSoundManager().playSound("kick-gritty");
    }

    @Override
    public void setSize(Vector2 size) {
        super.setSize(size);
        spritesLayers.get(0).frameLength = size.len() * 0.03f;
    }

    public boolean isDone() {
        return done;
    }

    private void setDone() {
        done = true;
    }
}
