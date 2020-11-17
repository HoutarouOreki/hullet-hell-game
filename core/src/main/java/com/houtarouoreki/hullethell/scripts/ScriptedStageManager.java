package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
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

    public ScriptedStageManager(World world, StageConfiguration script, AssetManager am) {
        this.world = world;
        waitingBodies = new PriorityQueue<ScriptedBody>();
        for (ScriptedBodyConfiguration bodyConf : script.bodies) {
            waitingBodies.add(new ScriptedBody(bodyConf));
        }
        assetManager = am;
        activeBodies = new ArrayList<ScriptedBody>();
    }

    public void update() {
        while (waitingBodies.size() > 0 && waitingBodies.peek().waitingActions.element().getScriptedTime() <= world.totalTimePassed) {
            ScriptedBody body = waitingBodies.remove();
            activeBodies.add(body);
            body.initialise(assetManager, world);
            world.bodies.add(body.controlledBody);
        }
        for (ScriptedBody body : activeBodies) {
            body.update();
        }
    }
}
