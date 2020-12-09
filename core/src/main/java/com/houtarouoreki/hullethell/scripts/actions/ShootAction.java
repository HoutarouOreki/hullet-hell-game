package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.helpers.ParsingHelpers;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class ShootAction extends ScriptedAction {
    private Vector2 bulletInitialVelocity;
    private String bulletType;

    @Override
    protected void performAction() {
        Bullet bullet = new Bullet(game, bulletType);
        bullet.setVelocity(bulletInitialVelocity);
        bullet.setPosition(body.getPosition());
        bullet.setTeam(body.getTeam());
        world.bodies.add(bullet);
        bullet.setSection(section);
        ((Ship)body).registerBullet(bullet);
        setFinished();
    }

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        bulletInitialVelocity = ParsingHelpers.vector2fromStrings(arguments.get(1), arguments.get(2));
        bulletType = arguments.get(0);
    }

    @Override
    public int bodiesAmount() {
        return 1;
    }
}
