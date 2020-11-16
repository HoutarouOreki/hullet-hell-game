package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Bullet;
import com.houtarouoreki.hullethell.entities.Environmental;
import com.houtarouoreki.hullethell.entities.Ship;

import java.util.LinkedList;
import java.util.Queue;

public class ScriptedBody {
    public String type;
    public String name;
    public String configName;
    public Queue<ScriptedAction> actions = new LinkedList<ScriptedAction>();
    public Body controlledBody;

    public void initialise(AssetManager assetManager) {
        controlledBody = createBodyFromScript(type, assetManager);
    }

    public void update() {
    }

    private Body createBodyFromScript(String bodyClass, AssetManager assetManager) {
//        Pattern pattern = Pattern.compile("(.*): \"((.*)\\/(.*))\"");
//        Matcher matcher = pattern.matcher(line);
//        String name = matcher.group(1);
//        String path = matcher.group(2);
//        String bodyClass = matcher.group(3);
//        String configName = matcher.group(4);

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
