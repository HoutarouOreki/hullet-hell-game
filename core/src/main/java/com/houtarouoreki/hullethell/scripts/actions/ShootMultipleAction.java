package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionFloatArg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShootMultipleAction extends ScriptedAction {
    private static final Pattern at_angle_pattern = Pattern.compile("at (\\d+(?:\\.\\d+)?)(?: deg(?:rees?)?|°) angle");
    private static final Pattern spacing_pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)(?: deg(?:rees?)?|°) spacing");
    protected String bulletType;
    private int amount;
    private float direction;
    private float spread;
    private float speed;

    @Override
    protected void performAction() {
        for (int i = 0; i < amount; i++) {
            float directionDegrees = getBulletRotation(i);
            Vector2 initialVelocity = new Vector2(0, 1).rotated(directionDegrees, true)
                    .scl(speed);
            Bullet bullet = new Bullet(bulletType);
            bullet.setVelocity(initialVelocity);
            bullet.setPosition(body.getPosition());
            if (body.getCollisionBodyManager().getTeam() == CollisionTeam.PLAYER_SHIP)
                bullet.getCollisionBodyManager().setTeam(CollisionTeam.PLAYER_BULLETS);
            else
                bullet.getCollisionBodyManager().setTeam(CollisionTeam.ENEMY_BULLETS);
            world.addBody(bullet);
            bullet.setSection(section);
        }
        setFinished();
        HulletHellGame.getSoundManager().playSound("laser2", 0.5f);
    }

    protected float getBulletRotation(int bulletNumber) {
        return this.direction - spread * (amount - 1) * 0.5f + spread * bulletNumber;
    }

    @Override
    public int bodiesAmount() {
        return amount;
    }

    @Override
    protected void addArgumentsInfo() {
        addBulletsPerShotArg(this::setBulletTypeAndAmount, false);
        parser.floatArgs.add(new ActionFloatArg(
                "Angle",
                null,
                "at 4.2 degree angle",
                at_angle_pattern,
                this::setDirection,
                true
        ));
        parser.floatArgs.add(new ActionFloatArg(
                "Spacing",
                "How many degrees there are between each bullet",
                "30 degree spacing",
                spacing_pattern,
                this::setSpread,
                false
        ));
        addSpeedArg(this::setSpeed, false);
    }

    protected void setBulletTypeAndAmount(Matcher matcher) {
        amount = Integer.parseInt(matcher.group("amount"));
        bulletType = matcher.group("type");
    }

    protected void setDirection(float direction) {
        this.direction = direction;
    }

    protected void setSpeed(float speed) {
        this.speed = speed;
    }

    protected float getSpread() {
        return spread;
    }

    protected int getAmount() {
        return amount;
    }

    protected void setSpread(float spread) {
        this.spread = spread;
    }
}
