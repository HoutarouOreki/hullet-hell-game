package com.houtarouoreki.hullethell.scripts;

import com.badlogic.gdx.assets.AssetManager;
import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.scripts.actions.MoveToAction;
import com.houtarouoreki.hullethell.scripts.actions.ShootAction;
import com.houtarouoreki.hullethell.scripts.actions.ShootMultipleAction;

import java.util.List;

public abstract class ScriptedAction {
    public String type;
    public List<String> arguments;
    public ScriptedBody scriptedBody;
    public Body body;
    protected World world;
    protected AssetManager assetManager;
    protected ScriptedSection section;
    private double scriptedTime;
    private double totalTime;
    private int ticks;
    private boolean finished;

    public static ScriptedAction createScriptedAction(ScriptedActionConfiguration conf, ScriptedBody body) {
        ScriptedAction a;
        if (conf.type.equals("moveTo")) {
            a = new MoveToAction();
        } else if (conf.type.equals("shoot")) {
            a = new ShootAction();
        } else if (conf.type.equals("shootMultipleRadius")) {
            a = new ShootMultipleAction();
        } else {
            throw new Error("Could not find action of type \"" + conf.type + "\"");
        }
        a.scriptedTime = conf.scriptedTime;
        a.arguments = conf.arguments;
        a.scriptedBody = body;
        return a;
    }

    public double getScriptedTime() {
        return scriptedTime;
    }

    protected abstract void performAction();

    protected void initialise(AssetManager assetManager, World world, ScriptedSection section, Body body) {
        this.assetManager = assetManager;
        this.world = world;
        this.section = section;
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
