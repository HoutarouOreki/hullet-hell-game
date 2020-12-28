package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionStringArg;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionVector2Arg;

import java.util.regex.Pattern;

public class ShootAction extends ScriptedAction {
    private static final Pattern type_pattern = Pattern.compile("type (\\w+)");
    private static final Pattern velocity_pattern = Pattern.compile("velocity \\(\\d+(?:\\.\\d+)?, \\d+(?:\\.\\d+)?\\)");
    private Vector2 bulletVelocity;
    private String bulletType;

    @Override
    protected void performAction() {
        Bullet bullet = new Bullet(bulletType);
        bullet.setVelocity(bulletVelocity);
        bullet.setPosition(body.getPosition());
        bullet.getCollisionBodyManager().setTeam(body.getCollisionBodyManager().getTeam());
        world.addBody(bullet);
        bullet.setSection(section);
        HulletHellGame.getSoundManager().playSound("laser1", 0.3f);
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 1;
    }

    @Override
    protected void addArgumentsInfo() {
        parser.stringArgs.add(new ActionStringArg(
                "Bullet type",
                null,
                "type hologramBullet",
                type_pattern,
                this::setBulletType,
                false
        ));
        parser.vector2Args.add(new ActionVector2Arg(
                "Velocity",
                null,
                "velocity (1.54, 2.2)",
                velocity_pattern,
                this::setBulletVelocity,
                false
        ));
    }

    private void setBulletType(String bulletType) {
        this.bulletType = bulletType;
    }

    private void setBulletVelocity(Vector2 bulletVelocity) {
        this.bulletVelocity = bulletVelocity;
    }
}
