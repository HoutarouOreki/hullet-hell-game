package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedActionConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.actions.DialogueAction;

import java.util.Iterator;

public class ScriptedDialogueSection extends ScriptedSection {
    public ScriptedDialogueSection(World world, ScriptedSectionConfiguration conf, DialogueBox dialogueBox) {
        super(world, conf, dialogueBox);
    }

    @Override
    protected void generateWaitingActions(ScriptedSectionConfiguration conf, DialogueBox dialogueBox) {
        for (ScriptedActionConfiguration actionConf : conf.actions) {
            ScriptedAction action = new DialogueAction(dialogueBox);
            action.scriptedTime = actionConf.scriptedTime;
            action.arguments = actionConf.arguments;
            waitingActions.add(action);
        }
    }

    @Override
    public void update(double delta) {
        timePassed += delta;

        while (waitingActions.size() > 0) {
            ScriptedAction action = waitingActions.remove();
            action.initialise(world, this, null);
            currentActions.add(action);
        }

        Iterator<ScriptedAction> i = currentActions.iterator();
        while (i.hasNext()) {
            ScriptedAction action = i.next();
            action.update();
            if (action.isFinished()) {
                i.remove();
            }
        }
    }
}
