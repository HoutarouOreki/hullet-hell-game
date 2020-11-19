package com.houtarouoreki.hullethell.scripts.actions;

public class ShootCircleAction extends ShootMultipleAction {
    @Override
    protected void initialiseArguments() {
        bulletType = arguments.get(0);
        amount = Integer.parseInt(arguments.get(1));
        speed = Double.parseDouble(arguments.get(2));
        direction = arguments.size() > 3 ? Double.parseDouble(arguments.get(3)) : 0;
        spread = 360f / amount;
    }
}
