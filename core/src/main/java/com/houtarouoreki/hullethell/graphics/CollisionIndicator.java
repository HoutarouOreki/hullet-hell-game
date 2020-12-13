package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.environment.Finishable;
import com.houtarouoreki.hullethell.environment.Updatable;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import org.mini2Dx.core.graphics.Graphics;

public class CollisionIndicator implements Updatable, Finishable, Renderable {
    private final float collisionEffectDuration = 0.25f;
    private final Vector2 position;
    private float timeLeft;

    public CollisionIndicator(CollisionResult collisionResult) {
        position = collisionResult.position;
        timeLeft = collisionEffectDuration;
    }

    public void update(float delta) {
        timeLeft -= delta;
    }

    public void render(Graphics g) {
        g.setColor(new Color(1, 1, 1,
                Interpolation.sineIn.apply(1, -0.2f,
                        getCompletionPercentage())));
        RenderHelpers.fillWorldCircle(position,
                0.5f * Interpolation.pow3Out
                        .apply(getCompletionPercentage()), g);
    }

    private float getCompletionPercentage() {
        return (collisionEffectDuration - timeLeft) / collisionEffectDuration;
    }

    public boolean isDone() {
        return timeLeft <= 0;
    }
}
