package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.PrimitiveBody;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.environment.Finishable;

public class BulletIndicator extends PrimitiveBody implements Finishable {
    private final Bullet bullet;
    private final float length = 0.4f;
    private float timeLeft;

    public BulletIndicator(Bullet bullet) {
        this.bullet = bullet;
        timeLeft = length;
        addTexture("effects/blurredCircle.png");
    }

    public void update(float delta) {
        timeLeft -= delta;
        float size = Interpolation.exp5.apply(0,
                bullet.getFarthestPointDistance() * 10, timeLeft / length);
        setSize(new Vector2(size, size));
        setPosition(bullet.getPosition());
    }

    public boolean isDone() {
        return timeLeft <= 0;
    }
}
