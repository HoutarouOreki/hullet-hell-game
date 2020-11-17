package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.entities.Environmental;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.environment.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptedBody {
    public String type;
    public String name;
    public String configName;
    public Queue<ScriptedAction> waitingActions = new LinkedList<ScriptedAction>();
    public List<ScriptedAction> currentActions = new ArrayList<ScriptedAction>();
    public Body controlledBody;
    private AssetManager assetManager;
    private World world;

    public ScriptedBody(String line) {
        Pattern pattern = Pattern.compile("(.*): \"((.*)/(.*))\"");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new Error("Could not find a match for scripted body line: " + line);
        }
        name = matcher.group(1);
        type = matcher.group(3);
        configName = matcher.group(4);
    }

    public void initialise(AssetManager assetManager, World world) {
        controlledBody = createBodyFromScript(type, assetManager);
        this.assetManager = assetManager;
        this.world = world;
    }

    public void update() {
        while (waitingActions.size() > 0 && waitingActions.peek().getScriptedTime() <= world.totalTimePassed) {
            ScriptedAction action = waitingActions.remove();
            currentActions.add(action);
            action.initialise(assetManager, world, controlledBody);
        }
        for (ScriptedAction action : currentActions) {
            action.update();
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
