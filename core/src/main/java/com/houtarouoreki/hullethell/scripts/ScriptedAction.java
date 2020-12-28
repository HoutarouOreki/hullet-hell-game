package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.actions.*;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionArgsParser;

import java.util.List;

public abstract class ScriptedAction implements Comparable<ScriptedAction> {
    protected final ActionArgsParser parser = new ActionArgsParser();
    public String type;
    public List<String> arguments;
    public ScriptedBody scriptedBody;
    public Body body;
    public double scriptedTime;
    protected World world;
    protected ScriptedSection section;
    private int ticks;
    private boolean finished;

    public static ScriptedAction createScriptedAction(ScriptedActionConfiguration conf,
                                                      ScriptedBody body,
                                                      DialogueBox dialogueBox) {
        ScriptedAction a = getScriptedAction(conf, dialogueBox);
        a.scriptedTime = conf.scriptedTime;
        a.arguments = conf.arguments;
        a.scriptedBody = body;
        return a;
    }

    private static ScriptedAction getScriptedAction(ScriptedActionConfiguration conf, DialogueBox dialogueBox) {
        switch (conf.type) {
            case "moveTo":
                return new MoveToAction();
            case "moveBezier":
                return new MoveBezierAction();
            case "shoot":
                return new ShootAction();
            case "shootMultiple":
                return new ShootMultipleAction();
            case "shootCircle":
                return new ShootCircleAction();
            case "playSong":
                return new PlaySongAction();
            case "loopSong":
                return new LoopSongAction();
            case "dialogueCharacter":
                return new DialogueCharacterAction(dialogueBox);
            case "dialogueText":
                return new DialogueTextAction(dialogueBox);
            case "randomAsteroid":
                return new RandomSplittingAsteroidAction();
            case "newItemQuest":
                return new NewItemQuest();
            case "setFlag":
                return new SetFlagAction();
            case "fadeOutMusic":
                return new FadeOutMusicAction();
            case "nullAction":
                return new NullAction();
            case "movePlayerTo":
                return new MovePlayerToAction();
            case "fadeInMusic":
                return new FadeInMusicAction();
            case "setLaserProperties":
                return new SetLaserPropertiesAction();
            case "holdPositionFor":
                return new HoldPositionForAction();
            case "shootSineCircleSeries":
                return new ShootSineCircleSeries();
            default:
                throw new Error("Could not find action of type \"" + conf.type + "\""
                        + "\nSource line: " + conf.line);
        }
    }

    @Override
    public int compareTo(ScriptedAction o) {
        return (int) Math.signum(getScriptedTime() - o.getScriptedTime());
    }

    public double getScriptedTime() {
        return scriptedTime;
    }

    public void update() {
        performAction();
        ticks++;
    }

    protected abstract void performAction();

    public double getTimeSinceStarted() {
        return section.getTimePassed() - getScriptedTime();
    }

    public int getTicks() {
        return ticks;
    }

    public boolean isFinished() {
        return finished;
    }

    public int bodiesAmount() {
        return 0;
    }

    protected abstract void addArgumentsInfo();

    protected void initialise(World world, ScriptedSection section, Body body) {
        this.world = world;
        this.section = section;
        this.body = body;
        parser.run(arguments);
    }

    protected void setFinished() {
        this.finished = true;
    }
}
