package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

public class ScriptedSection {
    public final Queue<ScriptedAction> waitingActions = new LinkedList<>();
    public final LinkedList<ScriptedAction> currentActions = new LinkedList<>();
    public final HashMap<String, Integer> flagsRequiredToStart;
    public final String name;
    protected final Queue<ScriptedBody> waitingBodies = new LinkedList<>();
    protected final LinkedList<ScriptedBody> activeBodies = new LinkedList<>();
    protected final World world;
    private final LinkedList<Body> physicalBodies = new LinkedList<>();
    protected double timePassed;
    protected int allBodiesAmount;
    private int bodiesRemovedAmount;

    public ScriptedSection(World world, ScriptedSectionConfiguration conf, DialogueBox dialogueBox) throws ScriptedSectionInitializationException {
        flagsRequiredToStart = new HashMap<>(conf.flagsRequiredToStart);
        this.world = world;
        name = conf.name;
        try {
            generateWaitingBodies(conf, dialogueBox);
            generateWaitingActions(conf, dialogueBox);
        } catch (ScriptedSectionGenerationException e) {
            throw new ScriptedSectionInitializationException(conf, e);
        }
    }

    protected void generateWaitingBodies(ScriptedSectionConfiguration conf, DialogueBox dialogueBox) throws ScriptedSectionGenerationException {
        for (ScriptedBodyConfiguration bodyConf : conf.bodies) {
            ScriptedBody body;
            try {
                body = new ScriptedBody(bodyConf, dialogueBox);
            } catch (ScriptedBodyInitializationException e) {
                throw new ScriptedSectionGenerationException(this, e);
            }
            waitingBodies.add(body);
            allBodiesAmount += body.getAllSubbodiesAmount();
        }
    }

    protected void generateWaitingActions(ScriptedSectionConfiguration conf, DialogueBox dialogueBox) throws ScriptedSectionGenerationException {
        for (ScriptedActionConfiguration actionConf : conf.actions) {
            ScriptedAction action;
            try {
                action = ScriptedAction.createScriptedAction(actionConf, null, dialogueBox);
            } catch (ActionTypeNotFoundException e) {
                throw new ScriptedSectionGenerationException(this, e);
            }
            waitingActions.add(action);
            allBodiesAmount += action.bodiesAmount();
        }
    }

    public boolean isFinished() {
        return waitingBodies.isEmpty() && activeBodies.isEmpty() && physicalBodies.isEmpty()
                && waitingActions.isEmpty() && currentActions.isEmpty();
    }

    private boolean isWaitingBodyReady(ScriptedBody scriptedBody) {
        return scriptedBody.waitingActions.isEmpty()
            || scriptedBody.waitingActions.peek().scriptedTime <= getTimePassed();
    }

    public void update(double delta) throws ScriptedSectionUpdateException {
        timePassed += delta;

        while (!waitingBodies.isEmpty() && isWaitingBodyReady(waitingBodies.peek())) {
            ScriptedBody body = waitingBodies.remove();
            activeBodies.add(body);
            body.initialise(world, this);
        }
        Iterator<ScriptedBody> i = activeBodies.iterator();
        while (i.hasNext()) {
            ScriptedBody body = i.next();
            try {
                body.update();
            } catch (ScriptedBodyUpdateException e) {
                throw new ScriptedSectionUpdateException(this, e);
            }
            if (body.isFinished()) {
                i.remove();
                physicalBodies.remove(body.controlledBody);
                if (body.isControlledBodyDead())
                    world.scriptedStageManager.incrementFlag(body.type + ":" + body.name);
            }
        }

        while (!waitingActions.isEmpty()
                && waitingActions.peek().getScriptedTime() <= getTimePassed()) {
            ScriptedAction action = waitingActions.remove();
            currentActions.add(action);
            try {
                action.initialise(world, this, null);
            } catch (ScriptedActionInitializationException e) {
                throw new ScriptedSectionUpdateException(this, e);
            }
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
        return physicalBodies.size();
    }

    public void registerBody(Body body) {
        physicalBodies.add(body);
    }

    public void unregisterBody(Body body) {
        physicalBodies.remove(body);
        bodiesRemovedAmount++;
    }

    public int getAllBodiesAmount() {
        return allBodiesAmount;
    }

    public int getBodiesRemovedAmount() {
        return bodiesRemovedAmount;
    }

    @Override
    public String toString() {
        return "   ScriptedSection " + name + ":\n" +
                "    waitingBodies (" + waitingBodies.size() + "): " + waitingBodies.stream().map(scriptedBody -> scriptedBody.name).collect(Collectors.joining(", ")) + '\n' +
                "    activeBodies (" + activeBodies.size() + "): \n" + activeBodies.stream().map(Object::toString).collect(Collectors.joining("\n     "));
    }
}
