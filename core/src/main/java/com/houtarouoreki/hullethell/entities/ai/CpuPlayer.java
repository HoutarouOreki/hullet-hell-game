package com.houtarouoreki.hullethell.entities.ai;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.configurations.Configurations;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.entities.Entity;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.environment.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.helpers.Timer;

public class CpuPlayer {
    public final Entity entity;
    private final World world;
    private final Configurations configurations;
    private Ship targetShip;
    private final Timer updatePositionTimer;
    private Vector2 previousPosition;
    private Vector2 targetPosition;
    private float positionInterpolationProgress;
    private final Timer bulletTimer;
    private int bulletIntervalIndex;

    public CpuPlayer(Entity entity, World world, Configurations configurations) {
        this.entity = entity;
        this.world = world;
        this.configurations = configurations;
        updatePositionTimer = new Timer(4.5f);
        updatePositionTimer.setInterval(2.5f);
        bulletTimer = new Timer(0.5f);
        bulletTimer.setInterval(0.5f);
    }

    public void update(float delta) {
        if (targetShip == null) {
            setTargetShip();
        }
        if (updatePositionTimer.update(delta)) {
            previousPosition = entity.getPosition();
            targetPosition = new Vector2(world.viewArea.x * (0.7f + (float) Math.random() * 0.2f), targetShip.getPosition().y);
            positionInterpolationProgress = 0;
        }
        float positionInterpolationTime = 2;
        if (previousPosition != null && positionInterpolationProgress <= positionInterpolationTime) {
            positionInterpolationProgress += delta;
            entity.setPosition(new Vector2(Interpolation.sine.apply(previousPosition.x, targetPosition.x, positionInterpolationProgress / positionInterpolationTime),
                    Interpolation.sine.apply(previousPosition.y, targetPosition.y, positionInterpolationProgress / positionInterpolationTime)));
        }

        if (bulletTimer.update(delta)) {
            bulletIntervalIndex++;

            // 3 shots, 2 breaks
            if (Math.sin((bulletIntervalIndex - 1.5) * Math.PI * 0.4) + 0.5 > 0) {
                Bullet bullet = new Bullet(entity.assetManager, "Bullet 1");
                bullet.setPosition(new Vector2(entity.getPosition()));
                bullet.setTeam(entity.getTeam());
                bullet.setVelocity(new Vector2(targetShip.getPosition()).add(new Vector2(entity.getPosition()).scl(-1)).setLength(8));
                world.bodies.add(bullet);
            }
        }
    }

    private void setTargetShip() {
        for (Body body: world.bodies) {
            if (body.getTeam() == CollisionTeam.PLAYER) {
                targetShip = (Ship)body;
                return;
            }
        }
    }
}
