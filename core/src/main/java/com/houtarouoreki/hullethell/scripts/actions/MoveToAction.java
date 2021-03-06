package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionInterpolationArg;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionVector2Arg;

import java.util.regex.Pattern;

public class MoveToAction extends ScriptedAction {
    private Vector2 startingPosition;
    private Vector2 targetPosition;
    private float duration;
    private Interpolation interpolation = Interpolation.linear;

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
        float progress = (float) (getTimeSinceStarted() / duration);
        if (progress > 1) {
            progress = 1;
            setFinished();
        }
        Vector2 currentPosition = Vector2.interpolate(startingPosition, targetPosition, progress, interpolation);
        body.setPosition(currentPosition);
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void addArgumentsInfo() {
        parser.vector2Args.add(new ActionVector2Arg(
                "Target position",
                null,
                "(0.3, 0.9)",
                vector2_pattern,
                this::setTargetPosition,
                false
        ));
        addDurationArg(this::setDuration, true);
        parser.interpolationArgs.add(new ActionInterpolationArg(
                "Interpolation",
                null,
                "sineIn",
                Pattern.compile("(\\w+) interpolation"),
                this::setInterpolation,
                true
        ));
    }

    private void setTargetPosition(Vector2 targetPosition) {
        this.targetPosition = targetPosition.scl(World.viewArea);
    }

    private void setDuration(float duration) {
        this.duration = duration;
    }

    private void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }
}
