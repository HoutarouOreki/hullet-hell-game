package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.entities.Body;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World {
    private final float time_step_duration = 0.01f;
    public List<Body> bodies;
    public float totalTimePassed;
    private float bufferedTime;

    public World() {
        bodies = new ArrayList<Body>();
    }

    public void update(float delta, Vector2 viewArea) {
        bufferedTime += delta;
        while (bufferedTime >= time_step_duration) {
            physics(viewArea);
            bufferedTime -= time_step_duration;
            totalTimePassed += time_step_duration;
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
            body.physics(time_step_duration, viewArea);
        }
    }
}
