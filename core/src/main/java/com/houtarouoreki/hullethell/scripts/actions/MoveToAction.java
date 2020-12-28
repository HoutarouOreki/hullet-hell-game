package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

import java.util.regex.Pattern;

public class MoveToAction extends ScriptedAction {
    private Vector2 startingPosition;
    private Vector2 targetPosition;
    private double duration;
    private Interpolation interpolation;

    @Override
    protected void createArgumentCallbacks() {
        parser.vector2Callbacks.put(
                Pattern.compile("\\((\\d+(?:\\.\\d+)?, \\d+(?:\\.\\d+)?)\\)"),
                this::setTargetPosition
        );
        parser.floatCallbacks.put(
                Pattern.compile("over ([0-9]+(?:[.][0-9]+)?) s(?:econds?)?"),
                this::setDuration
        );
        parser.interpolationCallbacks.put(
                Pattern.compile("(\\w+) interpolation"),
                this::setInterpolation
        );
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void performAction() {
        if (duration == 0) {
            body.setPosition(targetPosition);
            setFinished();
            return;
        }
        if (getTicks() == 0) {
            startingPosition = body.getPosition();
        }
        float progress = (float) ((section.getTimePassed() - getScriptedTime()) / duration);
        if (progress > 1) {
            progress = 1;
            setFinished();
        }
        float x = interpolation.apply(startingPosition.x, targetPosition.x, progress);
        float y = interpolation.apply(startingPosition.y, targetPosition.y, progress);
        body.setPosition(new Vector2(x, y));
    }

    private void setTargetPosition(Vector2 targetPosition) {
        this.targetPosition = targetPosition;
    }

    private void setDuration(double duration) {
        this.duration = duration;
    }

    private void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }
}
