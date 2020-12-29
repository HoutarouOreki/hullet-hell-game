package com.houtarouoreki.hullethell.scripts.actions;

import java.util.regex.Matcher;

public class ShootCircleAction extends ShootMultipleAction {
    @Override
    protected void addArgumentsInfo() {
        super.addArgumentsInfo();
        parser.floatArgs.removeIf(actionFloatArg -> actionFloatArg.name.equals("Spacing"));
    }

    @Override
    protected void setBulletTypeAndAmount(Matcher matcher) {
        super.setBulletTypeAndAmount(matcher);
        setSpread(360f / getAmount());
    }
}
