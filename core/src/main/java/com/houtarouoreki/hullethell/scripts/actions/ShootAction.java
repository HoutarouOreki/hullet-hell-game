package com.houtarouoreki.hullethell.scripts.actions;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.helpers.ParsingHelpers;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.ScriptedSection;

public class ShootAction extends ScriptedAction {
    private Vector2 bulletInitialVelocity;
    private String bulletType;

    @Override
    protected void performAction() {
        Bullet bullet = new Bullet(assetManager, bulletType);
        bullet.setVelocity(bulletInitialVelocity);
        bullet.setPosition(body.getPosition());
        bullet.setTeam(body.getTeam());
        world.bodies.add(bullet);
        setFinished();
    }

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        bulletInitialVelocity = ParsingHelpers.vector2fromStrings(arguments.get(1), arguments.get(2));
        bulletType = arguments.get(0);
    }
}
