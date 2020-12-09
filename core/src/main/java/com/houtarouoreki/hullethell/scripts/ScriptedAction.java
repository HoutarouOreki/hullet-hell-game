package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.scripts.actions.*;

import java.util.List;

public abstract class ScriptedAction implements Comparable<ScriptedAction> {
    public String type;
    public List<String> arguments;
    public ScriptedBody scriptedBody;
    public Body body;
    protected World world;
    protected HulletHellGame game;
    protected ScriptedSection section;
    private double scriptedTime;
    private double totalTime;
    private int ticks;
    private boolean finished;

    public static ScriptedAction createScriptedAction(ScriptedActionConfiguration conf, ScriptedBody body) {
        ScriptedAction a;
        if (conf.type.equals("moveTo")) {
            a = new MoveToAction();
        } else if (conf.type.equals("moveBezier")) {
            a = new MoveBezier();
        } else if (conf.type.equals("shoot")) {
            a = new ShootAction();
        } else if (conf.type.equals("shootMultipleRadius")) {
            a = new ShootMultipleAction();
        } else if (conf.type.equals("shootCircle")) {
            a = new ShootCircleAction();
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

    protected void initialise(HulletHellGame game, World world, ScriptedSection section, Body body) {
        this.game = game;
        this.world = world;
        this.section = section;
        this.body = body;
        initialiseArguments();
    }

    protected void initialiseArguments() {
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

    public abstract int bodiesAmount();

    protected void setFinished() {
        this.finished = true;
    }

    @Override
    public int compareTo(ScriptedAction o) {
        return (int) Math.signum(getScriptedTime() - o.getScriptedTime());
    }
}
