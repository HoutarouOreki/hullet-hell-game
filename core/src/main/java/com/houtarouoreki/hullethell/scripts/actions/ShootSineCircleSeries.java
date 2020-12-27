package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShootSineCircleSeries extends ScriptedAction {
    private String bulletType;
    private static final Pattern bulletsPerShotPattern = Pattern.compile("(?<amount>\\d+) (?<type>\\w+) per shot");
    private int bulletsPerShot;
    private static final Pattern durationPattern = Pattern.compile("spanning (\\d+(?:\\.\\d+)?) seconds?");
    private float duration;
    private static final Pattern intervalsPattern = Pattern.compile("(\\d+(?:\\.\\d+)?) seconds? intervals?");
    private float intervalDuration;
    private static final Pattern maxRotationPattern = Pattern.compile("max (\\d+(?:\\.\\d+)?) degrees? rotation");
    private float maxRotation;
    private static final Pattern speedPattern = Pattern.compile("(\\d+(?:\\.\\d+)?) speed");
    private float speed;
    private static final Pattern repeatsPattern = Pattern.compile("(\\d+(?:\\.\\d+)?) repeats?");
    private int shotsShot = 0;
    private float repeats;

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        for (String s : arguments)
            initialiseArgument(s);
    }

    private void initialiseArgument(String s) {
        Matcher matcher = bulletsPerShotPattern.matcher(s);
        if (matcher.matches()) {
            bulletsPerShot = Integer.parseInt(matcher.group("amount"));
            bulletType = matcher.group("type");
        }
        if ((matcher = durationPattern.matcher(s)).matches())
            duration = parseFloatFirstGroup(matcher);
        if ((matcher = intervalsPattern.matcher(s)).matches())
            intervalDuration = parseFloatFirstGroup(matcher);
        if ((matcher = maxRotationPattern.matcher(s)).matches())
            maxRotation = parseFloatFirstGroup(matcher);
        if ((matcher = speedPattern.matcher(s)).matches())
            speed = parseFloatFirstGroup(matcher);
        if ((matcher = repeatsPattern.matcher(s)).matches())
            repeats = parseFloatFirstGroup(matcher);
    }

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
}
