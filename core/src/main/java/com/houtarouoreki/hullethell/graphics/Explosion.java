package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.PrimitiveBody;
import com.houtarouoreki.hullethell.bindables.ValueChangeListener;
import com.houtarouoreki.hullethell.environment.Finishable;

public class Explosion extends PrimitiveBody implements Finishable {
    private boolean done;

    public Explosion(Vector2 position) {
        addAnimation("effects/Explosion", 6, 5);
        spritesLayers.get(0).frameIndex.addListener(new ValueChangeListener<Integer>() {
            @Override
            public void onValueChanged(Integer oldValue, Integer newValue) {
                if (oldValue == 5 && newValue == 0) {
                    setDone();
                }
            }
        });
        setPosition(position);
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
