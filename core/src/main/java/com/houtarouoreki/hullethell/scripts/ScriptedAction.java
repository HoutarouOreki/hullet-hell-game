package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.actions.*;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.*;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.exceptions.RequiredArgumentNotFoundException;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.exceptions.UninterpretedArgumentStringException;
import com.houtarouoreki.hullethell.scripts.exceptions.ActionTypeNotFoundException;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedActionInitializationException;

import java.util.List;
import java.util.regex.Pattern;

public abstract class ScriptedAction implements Comparable<ScriptedAction> {
    protected static final Pattern match_everything_pattern = Pattern.compile("^(.*)$");
    protected static final Pattern vector2_pattern
            = Pattern.compile("\\((-?\\d+(?:\\.\\d+)?, -?\\d+(?:\\.\\d+)?)\\)");
    protected static final Pattern bullets_per_shot_pattern
            = Pattern.compile("(?<amount>\\d+) (?<type>\\w+) per shot");
    protected static final Pattern duration_pattern
            = Pattern.compile("(?:spanning |over |for )([0-9]+(?:[.][0-9]+)?)(?: seconds?| ?s)");
    protected static final Pattern intervals_pattern
            = Pattern.compile("(\\d+(?:\\.\\d+)?) ?s(?:\\w+)?(?: long)? intervals?");
    protected static final Pattern max_rotation_pattern
            = Pattern.compile("max (\\d+(?:\\.\\d+)?)(?:°| degrees?)(?: of)? ?rotation");
    protected static final Pattern speed_pattern
            = Pattern.compile("(\\d+(?:\\.\\d+)?)(?: ?m| meters?|) speed");
    protected static final Pattern repeats_pattern
            = Pattern.compile("(\\d+(?:\\.\\d+)?) repeats?");
    protected static final Pattern item_amount_pattern
            = Pattern.compile("(?<itemAmount>\\d+) (?<itemName>\\w+)");
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
    private boolean initialised;
    public int scriptFileLineNumber;

    public static ScriptedAction createScriptedAction(ScriptedActionConfiguration conf,
                                                      ScriptedBody body,
                                                      DialogueBox dialogueBox) throws ActionTypeNotFoundException {
        ScriptedAction a = getScriptedAction(conf, dialogueBox);
        a.scriptedTime = conf.scriptedTime;
        a.arguments = conf.arguments;
        a.scriptedBody = body;
        a.type = conf.type;
        a.scriptFileLineNumber = conf.scriptFileLineNumber;
        a.addArgumentsInfo();
        return a;
    }

    private static ScriptedAction getScriptedAction(ScriptedActionConfiguration conf, DialogueBox dialogueBox) throws ActionTypeNotFoundException {
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
            case "holdPosition":
                return new HoldPositionForAction();
            case "shootSineCircleSeries":
                return new ShootSineCircleSeries();
            case "shootCircleSeries":
                return new ShootCircleSeriesAction();
            default:
                throw new ActionTypeNotFoundException(conf);
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
        if (!initialised)
            throw new RuntimeException("Scripted action not initialised.");
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

    protected void initialise(World world, ScriptedSection section, Body body) throws ScriptedActionInitializationException {
        this.world = world;
        this.section = section;
        this.body = body;
        try {
            parser.run(arguments);
            initialised = true;
        } catch (RequiredArgumentNotFoundException | UninterpretedArgumentStringException e) {
            throw new ScriptedActionInitializationException(this, e);
        }
    }

    protected void setFinished() {
        this.finished = true;
    }

    protected void addDurationArg(ActionArgsParserFloatSetter callback, boolean optional) {
        parser.floatArgs.add(new ActionFloatArg(
                "Duration",
                null,
                "spanning 9.8 seconds",
                duration_pattern,
                callback,
                optional
        ));
    }

    protected void addIntervalArg(ActionArgsParserFloatSetter callback, boolean optional) {
        parser.floatArgs.add(new ActionFloatArg(
                "Interval duration",
                null,
                "2 seconds intervals",
                intervals_pattern,
                callback,
                optional
        ));
    }

    protected void addMaxRotationArg(ActionArgsParserFloatSetter callback, boolean optional) {
        parser.floatArgs.add(new ActionFloatArg(
                "Max rotation",
                null,
                "max 34 degrees of rotation",
                max_rotation_pattern,
                callback,
                optional
        ));
    }

    protected void addSpeedArg(ActionArgsParserFloatSetter callback, boolean optional) {
        parser.floatArgs.add(new ActionFloatArg(
                "Speed",
                null,
                "4 speed",
                speed_pattern,
                callback,
                optional
        ));
    }

    protected void addRepeatsArg(ActionArgsParserFloatSetter callback, boolean optional) {
        parser.floatArgs.add(new ActionFloatArg(
                "Repeats",
                "How many times the actions is repeated during its duration",
                "3 repeats",
                repeats_pattern,
                callback,
                optional
        ));
    }

    protected void addBulletsPerShotArg(ActionArgsParserMatcherSetter callback, boolean optional) {
        parser.matcherArgs.add(new ActionMatcherArg(
                "Bullet type and amount",
                "What bullets to use and how many of them in a single shot",
                "12 hologramBullet per shot",
                bullets_per_shot_pattern,
                callback,
                optional
        ));
    }
}
