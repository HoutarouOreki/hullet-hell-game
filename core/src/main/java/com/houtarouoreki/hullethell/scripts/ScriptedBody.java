package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.entities.*;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;

import java.util.*;

public class ScriptedBody implements Comparable<ScriptedBody> {
    public final String type;
    public final String name;
    public final String configName;
    public final Queue<ScriptedAction> waitingActions = new LinkedList<>();
    public final List<ScriptedAction> currentActions = new ArrayList<>();
    public Body controlledBody;
    private int allSubbodiesAmount;
    private World world;
    private ScriptedSection section;

    public ScriptedBody(ScriptedBodyConfiguration conf, DialogueBox dialogueBox) {
        type = conf.type;
        name = conf.name;
        configName = conf.configName;
        for (ScriptedActionConfiguration actionConf : conf.actions) {
            ScriptedAction action = ScriptedAction
                    .createScriptedAction(actionConf, this, dialogueBox);
            waitingActions.add(action);
            allSubbodiesAmount += action.bodiesAmount();
        }
    }

    public boolean isFinished() {
        if (controlledBody.isRemoved()) {
            return true;
        }
        if (controlledBody instanceof Entity && !((Entity) controlledBody).isAlive()) {
            return true;
        }
        if (currentActions.size() == 0 && waitingActions.size() == 0) {
            world.removeBody(controlledBody);
            return true;
        }
        return false;
    }

    public void initialise(World world, ScriptedSection section) {
        controlledBody = createBodyFromScript(type);
        controlledBody.name = name;
        this.world = world;
        this.section = section;
    }

    public void update() {
        while (waitingActions.size() > 0 && waitingActions.peek().getScriptedTime() <= section.getTimePassed()) {
            ScriptedAction action = waitingActions.remove();
            currentActions.add(action);
            action.initialise(world, section, controlledBody);
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
            default:
                throw new Error("Error creating body from script");
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
}
