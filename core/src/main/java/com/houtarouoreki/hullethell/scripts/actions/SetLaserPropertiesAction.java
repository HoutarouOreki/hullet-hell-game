package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.entities.Laser;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class SetLaserPropertiesAction extends ScriptedAction {
    private float width;
    private float length;
    private float rotationSpeed;
    private float damage;

    @Override
    protected void performAction() {
        ((Laser) body).setWidth(width);
        ((Laser) body).setLength(length);
        ((Laser) body).setRotationSpeed(rotationSpeed);
        ((Laser) body).damage = damage;
        setFinished();
    }

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        width = Float.parseFloat(arguments.get(0));
        length = Float.parseFloat(arguments.get(1));
        rotationSpeed = Float.parseFloat(arguments.get(2));
        damage = Float.parseFloat(arguments.get(3));
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
