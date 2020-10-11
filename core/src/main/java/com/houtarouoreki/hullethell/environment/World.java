package com.houtarouoreki.hullethell.environment;

import com.houtarouoreki.hullethell.entities.Body;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final float time_step_duration = 0.01f;
    public List<Body> bodies;
    public float totalTimePassed;
    private float bufferedTime;

    public World() {
        bodies = new ArrayList<Body>();
    }

    public void update(float delta) {
        bufferedTime += delta;
        if (bufferedTime >= time_step_duration) {
            physics();
            bufferedTime -= time_step_duration;
            totalTimePassed += time_step_duration;
        }
    }

    protected void physics() {
        for (Body body : bodies) {
            body.physics(time_step_duration);
        }
    }
}
