package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.entities.*;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.exceptions.ActionTypeNotFoundException;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedActionInitializationException;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedBodyInitializationException;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedBodyUpdateException;

import java.util.*;

public class ScriptedBody implements Comparable<ScriptedBody> {
    public final String type;
    public final String name;
    public final String configName;
    public final Queue<ScriptedAction> waitingActions = new LinkedList<>();
    public final LinkedList<ScriptedAction> currentActions = new LinkedList<>();
    private final boolean wasInPreviousSections;
    private final boolean willBeInFutureSections;
    public Body controlledBody;
    private int allSubbodiesAmount;
    private World world;
    private ScriptedSection section;

    public ScriptedBody(ScriptedBodyConfiguration conf, DialogueBox dialogueBox) throws ScriptedBodyInitializationException {
        type = conf.type;
        name = conf.name;
        configName = conf.configName;
        wasInPreviousSections = conf.hasPreviousSection;
        willBeInFutureSections = conf.hasNextSection;
        for (ScriptedActionConfiguration actionConf : conf.actions) {
            ScriptedAction action;
            try {
                action = ScriptedAction
                        .createScriptedAction(actionConf, this, dialogueBox);
            } catch (ActionTypeNotFoundException e) {
                throw new ScriptedBodyInitializationException(conf, e);
            }
            waitingActions.add(action);
            allSubbodiesAmount += action.bodiesAmount();
        }
    }

    public boolean isControlledBodyDead() {
        return (controlledBody instanceof Entity) && !((Entity) controlledBody).isAlive();
    }

    public boolean isFinished() {
        if (controlledBody == null || controlledBody.isRemoved()) {
            return true;
        }
        if (isControlledBodyDead()) {
            return true;
        }
        if (currentActions.isEmpty() && waitingActions.isEmpty()) {
            if (!willBeInFutureSections)
                world.removeBody(controlledBody);
            return true;
        }
        return false;
    }

    public void initialise(World world, ScriptedSection section) {
        if (wasInPreviousSections) {
            controlledBody = world.getBody(name);
            if (controlledBody == null)
                return;
        }
        else {
            controlledBody = createBodyFromScript(type);
            controlledBody.name = name;
            world.addBody(controlledBody);
        }
        this.world = world;
        this.section = section;
    }

    public void update() throws ScriptedBodyUpdateException {
        if (controlledBody == null)
            return;

        while (!waitingActions.isEmpty() && waitingActions.peek().getScriptedTime() <= section.getTimePassed()) {
            ScriptedAction action = waitingActions.remove();
            currentActions.add(action);
            try {
                action.initialise(world, section, controlledBody);
            } catch (ScriptedActionInitializationException e) {
                throw new ScriptedBodyUpdateException(this, e);
            }
        }

        Iterator<ScriptedAction> i = currentActions.iterator();
        while (i.hasNext()) {
            ScriptedAction action = i.next();
            action.update();
            if (action.isFinished()) {
                i.remove();
            }
        }
    }

    private Body createBodyFromScript(String bodyClass) {
        switch (bodyClass) {
            case "ships":
                return new Ship(configName);
            case "bullets":
                return new Bullet(configName);
            case "environmentals":
                return new Environmental(configName);
            case "lasers":
                return new Laser();
            case "items":
                Item item = new Item(configName);
                item.setShouldDespawnOOBounds(false);
                return item;
            default:
                throw new Error("Could not create body of bodyClass \"" + bodyClass + "\".");
        }
    }

    public int getAllSubbodiesAmount() {
        return allSubbodiesAmount;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(ScriptedBody o) {
        ScriptedAction firstAction = waitingActions.peek();
        if (firstAction == null)
            return -1;
        ScriptedAction otherFirstAction = o.waitingActions.peek();
        if (otherFirstAction == null)
            return 1;
        return (int) Math.signum(firstAction.getScriptedTime() - otherFirstAction.getScriptedTime());
    }

    @Override
    public String toString() {
        return "ScriptedBody " + name + ", " + waitingActions.size() + " waiting, " + currentActions.size() + " active actions";
    }
}
