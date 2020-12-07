package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

import java.util.ArrayList;
import java.util.List;

public class MoveBezier extends ScriptedAction {
    private final List<Vector2> sourcePoints = new ArrayList<Vector2>();
    private double duration;

    @Override
    protected void initialiseArguments() {
        duration = Double.parseDouble(arguments.get(0));
        for (String s : arguments.get(1).split(" / ")) {
            String[] temp = s.split(" ");
            sourcePoints.add(new Vector2(Float.parseFloat(temp[0]), Float.parseFloat(temp[1])));
        }
    }

    private Vector2 calculateBezierPoint(double time) {
        return calculateBezierPoint((float)time, 0, sourcePoints.size() - 1);
    }

    private Vector2 calculateBezierPoint(float time, int firstPoint, int lastPoint) {
        if (firstPoint == lastPoint)
            return sourcePoints.get(firstPoint).cpy();
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
        body.setPosition(calculateBezierPoint(completion).scl(world.viewArea));
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
