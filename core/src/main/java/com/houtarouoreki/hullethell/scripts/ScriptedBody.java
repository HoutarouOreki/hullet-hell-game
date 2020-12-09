package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.entities.*;
import com.houtarouoreki.hullethell.environment.World;

import java.util.*;

public class ScriptedBody implements Comparable<ScriptedBody> {
    public final String type;
    public final String name;
    public final String configName;
    public Queue<ScriptedAction> waitingActions = new LinkedList<ScriptedAction>();
    public List<ScriptedAction> currentActions = new ArrayList<ScriptedAction>();
    public Body controlledBody;
    private int allSubbodiesAmount;
    private HulletHellGame game;
    private World world;
    private ScriptedSection section;

    public ScriptedBody(ScriptedBodyConfiguration conf) {
        type = conf.type;
        name = conf.name;
        configName = conf.configName;
        for (ScriptedActionConfiguration actionConf : conf.actions) {
            ScriptedAction action = ScriptedAction.createScriptedAction(actionConf, this);
            waitingActions.add(action);
            allSubbodiesAmount += action.bodiesAmount();
        }
    }

    public boolean isFinished() {
        if (controlledBody.isRemoved()) {
            return true;
        }
        if (controlledBody instanceof Entity && ((Entity) controlledBody).getHealth() == 0) {
            return true;
        }
        if (currentActions.size() == 0 && waitingActions.size() == 0) {
            world.bodies.remove(controlledBody);
            return true;
        }
        return false;
    }

    public void initialise(HulletHellGame game, World world, ScriptedSection section) {
        controlledBody = createBodyFromScript(type, game);
        this.game = game;
        this.world = world;
        this.section = section;
    }

    public void update() {
        while (waitingActions.size() > 0 && waitingActions.peek().getScriptedTime() <= section.getTimePassed()) {
            ScriptedAction action = waitingActions.remove();
            currentActions.add(action);
            action.initialise(game, world, section, controlledBody);
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

    private Body createBodyFromScript(String bodyClass, HulletHellGame game) {

        if (bodyClass.equals("ships")) {
            return new Ship(game.getAssetManager(), configName);
        } else if (bodyClass.equals("bullets")) {
            return new Bullet(game, configName);
        } else if (bodyClass.equals("environmentals")) {
            return new Environmental(game, configName);
        } else {
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
