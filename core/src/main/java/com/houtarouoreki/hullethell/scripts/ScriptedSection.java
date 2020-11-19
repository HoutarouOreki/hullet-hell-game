package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.environment.World;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ScriptedSection {
    private final Queue<ScriptedBody> waitingBodies;
    private final List<ScriptedBody> activeBodies;
    private final AssetManager assetManager;
    private final World world;
    private final String name;

    private double timePassed;

    public ScriptedSection(AssetManager assetManager, World world, ScriptedSectionConfiguration conf) {
        this.assetManager = assetManager;
        this.world = world;
        waitingBodies = new PriorityQueue<ScriptedBody>();
        for (ScriptedBodyConfiguration bodyConf : conf.bodies) {
            waitingBodies.add(new ScriptedBody(bodyConf));
        }
        activeBodies = new ArrayList<ScriptedBody>();
        name = conf.name;
    }

    public boolean isFinished() {
        return waitingBodies.size() == 0 && activeBodies.size() == 0;
    }

    public void update(double delta) {
        timePassed += delta;

        while (waitingBodies.size() > 0 && waitingBodies.peek().waitingActions.element().getScriptedTime() <= getTimePassed()) {
            ScriptedBody body = waitingBodies.remove();
            activeBodies.add(body);
            body.initialise(assetManager, world, this);
            world.bodies.add(body.controlledBody);
        }
        for (ScriptedBody body : activeBodies) {
            body.update();
            if (body.isFinished()) {
                activeBodies.remove(body);
            }
        }
    }

    public double getTimePassed() {
        return timePassed;
    }

    public String getName() {
        return name;
    }

    public int getWaitingBodiesCount() {
        return waitingBodies.size();
    }

    public int getActiveBodiesCount() {
        return activeBodies.size();
    }
}
