package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionVector2Arg;

import java.util.ArrayList;
import java.util.List;

public class MoveBezierAction extends ScriptedAction {
    private final List<Vector2> sourcePoints = new ArrayList<>();
    private double duration;

    private Vector2 calculateBezierPoint(double time) {
        return calculateBezierPoint((float) time, 0, sourcePoints.size() - 1);
    }

    private Vector2 calculateBezierPoint(float time, int firstPoint, int lastPoint) {
        if (firstPoint == lastPoint)
            return sourcePoints.get(firstPoint);
        Vector2 value = calculateBezierPoint(time, firstPoint, lastPoint - 1).scl(1 - time);
        return value.add(calculateBezierPoint(time, firstPoint + 1, lastPoint).scl(time));
    }

    @Override
    protected void performAction() {
        double completion = (section.getTimePassed() - getScriptedTime()) / (duration);
        if (completion > 1) {
            completion = 1;
            setFinished();
        }
        body.setPosition(calculateBezierPoint(completion).scl(World.viewArea));
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void addArgumentsInfo() {
        addDurationArg(this::setDuration, false);
        parser.vector2Args.add(new ActionVector2Arg(
                "Bezier point",
                "Add multiple of these to create a path, like in example",
                "(0.1, 0.2), (0.15, 0.3), (0.25, 0.35), (0.3, 0.4)",
                vector2_pattern,
                this::addPoint,
                false
        ));
    }

    private void addPoint(Vector2 point) {
        sourcePoints.add(point);
    }

    private void setDuration(Float duration) {
        this.duration = duration;
    }
}
