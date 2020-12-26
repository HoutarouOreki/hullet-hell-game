package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.quests.Quest;

public class ScriptedWhileSection extends ScriptedSection {
    private final String parameters;
    private final ScriptedSectionConfiguration conf;
    private final DialogueBox dialogueBox;

    public ScriptedWhileSection(World world, ScriptedSectionConfiguration conf, DialogueBox dialogueBox) {
        super(world, conf, dialogueBox);
        this.parameters = conf.parameters;
        this.conf = conf;
        this.dialogueBox = dialogueBox;
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        if (!waitingActions.isEmpty() || !waitingBodies.isEmpty())
            return;
        if (isSupposedToRestart()) {
            generateWaitingActions(conf, dialogueBox);
            generateWaitingBodies(conf, dialogueBox);
        }
    }

    @Override
    protected void generateWaitingBodies(ScriptedSectionConfiguration conf, DialogueBox dialogueBox) {
        for (ScriptedBodyConfiguration bodyConf : conf.bodies) {
            ScriptedBody body = new ScriptedBody(bodyConf, dialogueBox);
            for (ScriptedAction action : body.waitingActions)
                action.scriptedTime += getTimePassed();
            waitingBodies.add(body);
            allBodiesAmount += body.getAllSubbodiesAmount();
        }
    }

    @Override
    protected void generateWaitingActions(ScriptedSectionConfiguration conf, DialogueBox dialogueBox) {
        for (ScriptedActionConfiguration actionConf : conf.actions) {
            ScriptedAction action = ScriptedAction.createScriptedAction(actionConf, null, dialogueBox);
            action.scriptedTime += getTimePassed();
            waitingActions.add(action);
            allBodiesAmount += action.bodiesAmount();
        }
    }

    private boolean isSupposedToRestart() {
        if (parameters.startsWith("quest:")) {
            String questName = parameters.replace("quest:", "");
            for (Quest quest : world.questManager.quests) {
                if (quest.name.equals(questName) && quest.isDone())
                    return false;
            }
        }
        return world.scriptedStageManager.getFlagValue(parameters) <= 0;
    }

    @Override
    public boolean isFinished() {
        return super.isFinished() && !isSupposedToRestart();
    }
}
