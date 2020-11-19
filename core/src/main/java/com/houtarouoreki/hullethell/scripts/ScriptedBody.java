package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.entities.*;
import com.houtarouoreki.hullethell.environment.World;

import java.util.*;

public class ScriptedBody {
    public final String type;
    public final String name;
    public final String configName;
    public Queue<ScriptedAction> waitingActions = new LinkedList<ScriptedAction>();
    public List<ScriptedAction> currentActions = new ArrayList<ScriptedAction>();
    public Body controlledBody;
    private AssetManager assetManager;
    private World world;
    private ScriptedSection section;

    public ScriptedBody(ScriptedBodyConfiguration conf) {
        type = conf.type;
        name = conf.name;
        configName = conf.configName;
        for (ScriptedActionConfiguration actionConf : conf.actions) {
            waitingActions.add(ScriptedAction.createScriptedAction(actionConf, this));
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

    public void initialise(AssetManager assetManager, World world, ScriptedSection section) {
        controlledBody = createBodyFromScript(type, assetManager);
        this.assetManager = assetManager;
        this.world = world;
        this.section = section;
    }

    public void update() {
        while (waitingActions.size() > 0 && waitingActions.peek().getScriptedTime() <= section.getTimePassed()) {
            ScriptedAction action = waitingActions.remove();
            currentActions.add(action);
            action.initialise(assetManager, world, section, controlledBody);
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

    private Body createBodyFromScript(String bodyClass, AssetManager assetManager) {

        if (bodyClass.equals("ships")) {
            return new Ship(assetManager, configName);
        } else if (bodyClass.equals("bullets")) {
            return new Bullet(assetManager, configName);
        } else if (bodyClass.equals("environmentals")) {
            return new Environmental(assetManager, configName);
        } else {
            throw new Error("Error creating body from script");
        }
    }
}
