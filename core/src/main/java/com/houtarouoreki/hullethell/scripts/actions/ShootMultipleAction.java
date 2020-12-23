package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.helpers.VectorHelpers;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class ShootMultipleAction extends ScriptedAction {
    protected String bulletType;
    protected int amount;
    protected double direction;
    protected double spread;
    protected double speed;

    @Override
    protected void performAction() {
        for (int i = 0; i < amount; i++) {
            double directionDegrees = this.direction + spread * (-(amount - 1) * 0.5 + i);
            Vector2 initialVelocity = VectorHelpers.unitFromDegrees(directionDegrees)
                    .scl((float) speed);
            Bullet bullet = new Bullet(bulletType);
            bullet.setVelocity(initialVelocity);
            bullet.setPosition(body.getPosition());
            if (body.getTeam() == CollisionTeam.PLAYER_SHIP)
                bullet.setTeam(CollisionTeam.PLAYER_BULLETS);
            else
                bullet.setTeam(CollisionTeam.ENEMY_BULLETS);
            world.addBody(bullet);
            bullet.setSection(section);
            ((Ship) body).registerBullet(bullet);
        }
        setFinished();
        HulletHellGame.getSoundManager().playSound("laser2", 0.5f);
    }

    @Override
    protected void initialiseArguments() {
        bulletType = arguments.get(0);
        amount = Integer.parseInt(arguments.get(1));
        direction = Double.parseDouble(arguments.get(2));
        spread = Double.parseDouble(arguments.get(3));
        speed = Double.parseDouble(arguments.get(4));
    }

    @Override
    public int bodiesAmount() {
        initialiseArguments();
        return amount;
    }
}
