package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

import java.util.regex.Matcher;

public class ShootSineCircleSeries extends ScriptedAction {
    private String bulletType;
    private int bulletsPerShot;
    private float duration;
    private float intervalDuration;
    private float maxRotation;
    private float speed;
    private int shotsShot = 0;
    private float repeats;

    @Override
    protected void performAction() {
        if (section.getTimePassed() > duration + getScriptedTime()) {
            setFinished();
            return;
        }
        if (intervalDuration * shotsShot > getTimeSinceStarted())
            return;
        float rotation = (float) (maxRotation * Math.sin(getTimeSinceStarted() * 2 * Math.PI / duration * repeats));
        float angularSpacing = 360f / bulletsPerShot;
        for (int i = 0; i < bulletsPerShot; i++) {
            Bullet bullet = new Bullet(bulletType);
            bullet.setVelocity(new Vector2(0, speed)
                    .rotated(rotation + angularSpacing * i, true));
            bullet.setPosition(body.getPosition());
            bullet.getCollisionBodyManager().setTeam(body.getCollisionBodyManager().getTeam().getBulletTeam());
            world.addBody(bullet);
            bullet.setSection(section);
        }
        shotsShot++;
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void addArgumentsInfo() {
        addBulletsPerShotArg(this::setBulletTypeAndAmount, false);
        addDurationArg(this::setDuration, false);
        addIntervalArg(this::setIntervalDuration, false);
        addMaxRotationArg(this::setMaxRotation, false);
        addSpeedArg(this::setSpeed, false);
        addRepeatsArg(this::setRepeats, true);
    }

    private void setBulletTypeAndAmount(Matcher matcher) {
        bulletsPerShot = Integer.parseInt(matcher.group("amount"));
        bulletType = matcher.group("type");
    }

    private void setDuration(float duration) {
        this.duration = duration;
    }

    private void setIntervalDuration(float intervalDuration) {
        this.intervalDuration = intervalDuration;
    }

    private void setMaxRotation(float maxRotation) {
        this.maxRotation = maxRotation;
    }

    private void setSpeed(float speed) {
        this.speed = speed;
    }

    private void setRepeats(float repeats) {
        this.repeats = repeats;
    }
}
