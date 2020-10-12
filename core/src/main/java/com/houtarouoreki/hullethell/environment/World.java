package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Entity;
import com.houtarouoreki.hullethell.entities.ai.CpuPlayer;
import com.houtarouoreki.hullethell.environment.collisions.CollisionManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World {
    public final Vector2 viewArea = new Vector2(36, 20);
    public List<Body> bodies;
    public List<CpuPlayer> cpus;
    public float totalTimePassed;
    private final float time_step_duration = 0.01f;
    private float bufferedTime;
    private final CollisionManager collisionManager;

    public World() {
        bodies = new ArrayList<Body>();
        cpus = new ArrayList<CpuPlayer>();
        collisionManager = new CollisionManager(bodies);
    }

    public void update(float delta) {
        bufferedTime += delta;
        while (bufferedTime >= time_step_duration) {
            physics(viewArea);
            bufferedTime -= time_step_duration;
            totalTimePassed += time_step_duration;
            updateAI(delta);
        }
    }

    private void updateAI(float delta) {
        Iterator<CpuPlayer> i = cpus.iterator();
        while (i.hasNext()) {
            CpuPlayer cpu = i.next();
            if (!cpu.entity.isAlive()) {
                i.remove();
            } else {
                cpu.update(delta);
            }
        }
    }

    protected void physics(Vector2 viewArea) {
        Iterator<Body> i = bodies.iterator();
        while (i.hasNext()) {
            Body body = i.next();
            if (body.getPosition().x < -10 || body.getPosition().y < -10 ||
                    body.getPosition().x > viewArea.x + 10 || body.getPosition().y > viewArea.y + 10) {
                // usuń jeśli 10m poza planszą
                i.remove();
                continue;
            }
            if (body instanceof Entity && ((Entity) body).getHealth() <= 0) { // check entity's health
                i.remove();
                continue;
            }
            body.physics(time_step_duration, viewArea);
        }
        collisionManager.RunCollisions();
    }
}
