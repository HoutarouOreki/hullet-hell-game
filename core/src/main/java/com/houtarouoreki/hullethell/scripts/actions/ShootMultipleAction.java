package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.helpers.VectorHelpers;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class ShootMultipleAction extends ScriptedAction {
    protected int amount;
    protected double direction;
    protected double spread;
    protected double speed;
    protected String bulletType;

    @Override
    protected void performAction() {
        for (int i = 0; i < amount; i++) {
            double directionDegrees = this.direction + spread * (-(amount - 1) * 0.5 + i);
            Vector2 initialVelocity = VectorHelpers.unitFromDegrees(directionDegrees).scl((float) speed);
            Bullet bullet = new Bullet(assetManager, bulletType);
            bullet.setVelocity(initialVelocity);
            bullet.setPosition(body.getPosition());
            bullet.setTeam(body.getTeam());
            world.bodies.add(bullet);
            bullet.setSection(section);
        }
        setFinished();
    }

    @Override
    protected void initialiseArguments() {
        direction = Double.parseDouble(arguments.get(2));
        spread = Double.parseDouble(arguments.get(3));
        bulletType = arguments.get(0);
        amount = Integer.parseInt(arguments.get(1));
        speed = Double.parseDouble(arguments.get(4));
    }
}
