package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class NullAction extends ScriptedAction {
    @Override
    protected void performAction() {
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
