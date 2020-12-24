package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Entity;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;

import java.util.*;

public class ScriptedSection {
    public final Queue<ScriptedAction> waitingActions;
    public final List<ScriptedAction> currentActions;
    public final HashMap<String, Integer> flagsRequiredToStart;
    protected final World world;
    protected final Queue<ScriptedBody> waitingBodies;
    protected final List<ScriptedBody> activeBodies;
    private final List<Body> bodies;
    public final String name;
    protected double timePassed;
    protected int allBodiesAmount;
    private int bodiesRemovedAmount;

    public ScriptedSection(World world, ScriptedSectionConfiguration conf, DialogueBox dialogueBox) {
        flagsRequiredToStart = new HashMap<>(conf.flagsRequiredToStart);
        this.world = world;
        waitingBodies = new PriorityQueue<>();
        generateWaitingBodies(conf, dialogueBox);
        activeBodies = new ArrayList<>();
        bodies = new ArrayList<>();
        name = conf.name;
        waitingActions = new LinkedList<>();
        currentActions = new ArrayList<>();
        generateWaitingActions(conf, dialogueBox);
    }

    protected void generateWaitingBodies(ScriptedSectionConfiguration conf, DialogueBox dialogueBox) {
        for (ScriptedBodyConfiguration bodyConf : conf.bodies) {
            ScriptedBody body = new ScriptedBody(bodyConf, dialogueBox);
            waitingBodies.add(body);
            allBodiesAmount += body.getAllSubbodiesAmount();
        }
    }

    protected void generateWaitingActions(ScriptedSectionConfiguration conf, DialogueBox dialogueBox) {
        for (ScriptedActionConfiguration actionConf : conf.actions) {
            ScriptedAction action = ScriptedAction.createScriptedAction(actionConf, null, dialogueBox);
            waitingActions.add(action);
            allBodiesAmount += action.bodiesAmount();
        }
    }

    public boolean isFinished() {
        return waitingBodies.size() == 0 && activeBodies.size() == 0 && bodies.size() == 0
                && waitingActions.size() == 0 && currentActions.size() == 0;
    }

    public void update(double delta) {
        timePassed += delta;

        while (waitingBodies.size() > 0 && waitingBodies.peek()
                .waitingActions.element().getScriptedTime() <= getTimePassed()) {
            ScriptedBody body = waitingBodies.remove();
            activeBodies.add(body);
            body.initialise(world, this);
        }
        Iterator<ScriptedBody> i = activeBodies.iterator();
        while (i.hasNext()) {
            ScriptedBody body = i.next();
            body.update();
            if (body.isFinished()) {
                i.remove();
                if (body.isControlledBodyDead())
                    world.scriptedStageManager.incrementFlag(body.type + ":" + body.name);
            }
        }

        while (waitingActions.size() > 0
                && waitingActions.peek().getScriptedTime() <= getTimePassed()) {
            ScriptedAction action = waitingActions.remove();
            currentActions.add(action);
            action.initialise(world, this, null);
        }

        Iterator<ScriptedAction> j = currentActions.iterator();
        while (j.hasNext()) {
            ScriptedAction action = j.next();
            action.update();
            if (action.isFinished()) {
                j.remove();
            }
        }
    }

    public double getTimePassed() {
        return timePassed;
    }

    public int getWaitingBodiesCount() {
        return waitingBodies.size();
    }

    public int getActiveBodiesCount() {
        return activeBodies.size();
    }

    public int getWaitingActionsCount() {
        return waitingActions.size();
    }

    public int getCurrentActionsCount() {
        return currentActions.size();
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
