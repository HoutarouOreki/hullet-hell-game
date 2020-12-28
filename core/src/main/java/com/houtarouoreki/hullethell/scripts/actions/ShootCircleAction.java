package com.houtarouoreki.hullethell.scripts.actions;

public class ShootCircleAction extends ShootMultipleAction {
    @Override
    protected void addArgumentsInfo() {
        super.addArgumentsInfo();
        parser.floatArgs.removeIf(actionFloatArg -> actionFloatArg.name.equals("Spacing"));
        setSpread(360f / getAmount());
    }
}
