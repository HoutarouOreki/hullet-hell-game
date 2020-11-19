package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.helpers.ParsingHelpers;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class MoveToAction extends ScriptedAction {
    private Vector2 startingPosition;
    private Vector2 targetPosition;
    private double duration;

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        targetPosition = ParsingHelpers.vector2fromStrings(arguments.get(0), arguments.get(1)).scl(world.viewArea);
        if (arguments.size() < 3) {
            duration = 0;
        } else {
            duration = Double.parseDouble(arguments.get(2));
        }
    }

    @Override
    protected void performAction() {
        if (duration == 0) {
            body.setPosition(targetPosition.cpy());
            setFinished();
            return;
        }
        if (getTicks() == 0) {
            startingPosition = new Vector2(body.getPosition());
        }
        float progress = (float) ((section.getTimePassed() - getScriptedTime()) / duration);
        if (progress > 1) {
            progress = 1;
            setFinished();
        }
        float x = Interpolation.linear.apply(startingPosition.x, targetPosition.x, progress);
        float y = Interpolation.linear.apply(startingPosition.y, targetPosition.y, progress);
        body.setPosition(new Vector2(x, y));
    }
}
