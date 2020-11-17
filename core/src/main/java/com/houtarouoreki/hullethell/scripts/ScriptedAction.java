package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.scripts.actions.MoveToAction;
import com.houtarouoreki.hullethell.scripts.actions.ShootAction;
import com.houtarouoreki.hullethell.scripts.actions.ShootMultipleAction;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ScriptedAction {
    public String type;
    public List<String> arguments;
    public ScriptedBody scriptedBody;
    public Body body;
    protected World world;
    protected AssetManager assetManager;
    private double scriptedTime;
    private double totalTime;
    private int ticks;
    private boolean finished;

    public static ScriptedAction createScriptedAction(String line, ScriptedBody body) {
        Pattern pattern = Pattern.compile("\\t+(\\d+(?:\\.\\d*)?)\\t+(\\w+):\\t+(.*)");
        Matcher match = pattern.matcher(line.split(" // ")[0]);
        if (!match.matches()) {
            throw new Error("Could not find a match for an action: " + line);
        }
        String type = match.group(2);
        ScriptedAction a;
        if (type.equals("moveTo")) {
            a = new MoveToAction();
        } else if (type.equals("shoot")) {
            a = new ShootAction();
        } else if (type.equals("shootMultipleRadius")) {
            a = new ShootMultipleAction();
        } else {
            throw new Error("Could not find action of type \"" + type + "\"");
        }
        a.scriptedTime = Double.parseDouble(match.group(1));
        a.arguments = Arrays.asList(match.group(3).split(", "));
        a.scriptedBody = body;
        return a;
    }

    public double getScriptedTime() {
        return scriptedTime;
    }

    protected abstract void performAction();

    protected void initialise(AssetManager assetManager, World world, Body body) {
        this.assetManager = assetManager;
        this.world = world;
        this.body = body;
    }

    public void update() {
        performAction();
        ticks++;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public int getTicks() {
        return ticks;
    }

    public boolean isFinished() {
        return finished;
    }

    protected void setFinished() {
        this.finished = true;
    }
}
