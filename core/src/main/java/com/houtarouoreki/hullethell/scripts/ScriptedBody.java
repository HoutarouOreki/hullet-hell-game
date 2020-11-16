package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.entities.Environmental;
import com.houtarouoreki.hullethell.entities.Ship;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptedBody {
    public String type;
    public String name;
    public String configName;
    public Queue<ScriptedAction> actions = new LinkedList<ScriptedAction>();
    public Body controlledBody;

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

    public void initialise(AssetManager assetManager) {
        controlledBody = createBodyFromScript(type, assetManager);
    }

    public void update() {
    }

    private Body createBodyFromScript(String bodyClass, AssetManager assetManager) {

        if (bodyClass.equals("ships")) {
            return new Ship(assetManager, configName);
        } else if (bodyClass.equals("bullets")) {
            return new Bullet(assetManager, configName);
        } else if (bodyClass.equals("environmentals")) {
            return new Environmental(assetManager, configName);
        } else {
            return null;
        }
    }
}
