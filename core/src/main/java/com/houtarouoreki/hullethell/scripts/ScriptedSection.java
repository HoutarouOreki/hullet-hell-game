package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;

import java.util.*;

public class ScriptedSection {
    private final Queue<ScriptedBody> waitingBodies;
    private final List<ScriptedBody> activeBodies;
    private final List<Body> bodies;
    private final HulletHellGame game;
    private final World world;
    private final String name;
    private int allBodiesAmount;
    private double timePassed;
    private int bodiesRemovedAmount;

    public ScriptedSection(HulletHellGame game, World world, ScriptedSectionConfiguration conf) {
        this.game = game;
        this.world = world;
        waitingBodies = new PriorityQueue<ScriptedBody>();
        for (ScriptedBodyConfiguration bodyConf : conf.bodies) {
            ScriptedBody body = new ScriptedBody(bodyConf);
            waitingBodies.add(body);
            allBodiesAmount += body.getAllSubbodiesAmount();
        }
        activeBodies = new ArrayList<ScriptedBody>();
        bodies = new ArrayList<Body>();
        name = conf.name;
    }

    public boolean isFinished() {
        return waitingBodies.size() == 0 && activeBodies.size() == 0 && bodies.size() == 0;
    }

    public void update(double delta) {
        timePassed += delta;

        if (waitingBodies.size() > 0)
            System.out.println(waitingBodies.peek().waitingActions.element().getScriptedTime() + " <= " + getTimePassed());
        while (waitingBodies.size() > 0 && waitingBodies.peek().waitingActions.element().getScriptedTime() <= getTimePassed()) {
            ScriptedBody body = waitingBodies.remove();
            activeBodies.add(body);
            body.initialise(game, world, this);
            world.bodies.add(body.controlledBody);
        }
        Iterator<ScriptedBody> i = activeBodies.iterator();
        while (i.hasNext()) {
            ScriptedBody body = i.next();
            body.update();
            if (body.isFinished()) {
                i.remove();
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

    public int getBodiesCount() {
        return bodies.size();
    }

    public void registerBody(Body body) {
        bodies.add(body);
    }

    public void unregisterBody(Body body) {
        bodies.remove(body);
        bodiesRemovedAmount++;
    }

    public int getAllBodiesAmount() {
        return allBodiesAmount;
    }

    public int getBodiesRemovedAmount() {
        return bodiesRemovedAmount;
    }
}
