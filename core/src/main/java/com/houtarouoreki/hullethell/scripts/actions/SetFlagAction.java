package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class SetFlagAction extends ScriptedAction {
    private String flag;

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        flag = arguments.get(0);
    }

    @Override
    protected void performAction() {
        world.scriptedStageManager.incrementFlag(flag);
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
