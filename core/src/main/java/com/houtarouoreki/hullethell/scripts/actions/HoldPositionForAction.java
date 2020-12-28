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
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void addArgumentsInfo() {
        addDurationArg(this::setDuration, false);
    }

    private void setDuration(Float duration) {
        this.duration = duration;
    }
}
