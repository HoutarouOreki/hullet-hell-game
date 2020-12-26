package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class HoldPositionForAction extends ScriptedAction {
    private Vector2 targetPosition;
    private float duration;

    @Override
    protected void performAction() {
        if (getTicks() == 0)
            targetPosition = body.getPosition();
        if (section.getTimePassed() > duration + scriptedTime)
            setFinished();
        body.setPosition(targetPosition);
    }

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        duration = Float.parseFloat(arguments.get(0));
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
