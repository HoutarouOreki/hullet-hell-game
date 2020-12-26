package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.helpers.ParsingHelpers;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class ShootAction extends ScriptedAction {
    private Vector2 bulletInitialVelocity;
    private String bulletType;

    @Override
    protected void performAction() {
        Bullet bullet = new Bullet(bulletType);
        bullet.setVelocity(bulletInitialVelocity);
        bullet.setPosition(body.getPosition());
        bullet.getCollisionBodyManager().setTeam(body.getCollisionBodyManager().getTeam());
        world.addBody(bullet);
        bullet.setSection(section);
        HulletHellGame.getSoundManager().playSound("laser1", 0.3f);
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
