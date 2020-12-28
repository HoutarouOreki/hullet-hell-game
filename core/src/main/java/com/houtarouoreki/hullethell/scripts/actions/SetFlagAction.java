package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionStringArg;

public class SetFlagAction extends ScriptedAction {
    private String flag;

    @Override
    protected void performAction() {
        world.scriptedStageManager.incrementFlag(flag);
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void addArgumentsInfo() {
        parser.stringArgs.add(new ActionStringArg(
                "Name",
                null,
                "someFlag2",
                match_everything_pattern,
                this::setFlagName,
                false
        ));
    }

    private void setFlagName(String s) {
        flag = s;
    }
}
