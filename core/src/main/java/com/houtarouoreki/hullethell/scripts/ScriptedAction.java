package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.actions.*;

import java.util.List;

public abstract class ScriptedAction implements Comparable<ScriptedAction> {
    public String type;
    public List<String> arguments;
    public ScriptedBody scriptedBody;
    public Body body;
    public double scriptedTime;
    protected World world;
    protected ScriptedStageManager scriptedStageManager;
    protected ScriptedSection section;
    private double totalTime;
    private int ticks;
    private boolean finished;

    public static ScriptedAction createScriptedAction(ScriptedActionConfiguration conf,
                                                      ScriptedBody body,
                                                      DialogueBox dialogueBox) {
        ScriptedAction a;
        if (conf.type.equals("moveTo")) {
            a = new MoveToAction();
        } else if (conf.type.equals("moveBezier")) {
            a = new MoveBezierAction();
        } else if (conf.type.equals("shoot")) {
            a = new ShootAction();
        } else if (conf.type.equals("shootMultipleRadius")) {
            a = new ShootMultipleAction();
        } else if (conf.type.equals("shootCircle")) {
            a = new ShootCircleAction();
        } else if (conf.type.equals("playSong")) {
            a = new PlaySongAction();
        } else if (conf.type.equals("loopSong")) {
            a = new LoopSongAction();
        } else if (conf.type.equals("dialogue")) {
            a = new DialogueAction(dialogueBox);
        } else if (conf.type.equals("randomAsteroid")) {
            a = new RandomSplittingAsteroidAction();
        } else if (conf.type.equals("newItemQuest")) {
            a = new NewItemQuest();
        } else if (conf.type.equals("setFlag")) {
            a = new SetFlagAction();
        } else {
            throw new Error("Could not find action of type \"" + conf.type + "\""
                    + "\nSource line: " + conf.line);
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

    protected void initialise(World world, ScriptedSection section, Body body) {
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
