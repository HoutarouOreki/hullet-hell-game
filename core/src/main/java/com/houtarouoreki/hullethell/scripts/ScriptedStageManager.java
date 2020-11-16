package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.environment.World;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ScriptedStageManager {
    private final Queue<ScriptedBody> waitingBodies;
    private final AssetManager assetManager;
    private final List<ScriptedBody> activeBodies;
    private final World world;

    public ScriptedStageManager(World world, List<ScriptedBody> bodies, AssetManager am) {
        this.world = world;
        waitingBodies = new PriorityQueue<ScriptedBody>(bodies);
        assetManager = am;
        activeBodies = new ArrayList<ScriptedBody>();
    }

    public void update() {
        while (waitingBodies.size() > 0 && waitingBodies.peek().actions.element().getScriptedTime() <= world.totalTimePassed) {
            ScriptedBody body = waitingBodies.remove();
            activeBodies.add(body);
            body.initialise(assetManager);
            world.bodies.add(body.controlledBody);
        }
        for (ScriptedBody body : activeBodies) {
            body.update();
        }
    }
}
