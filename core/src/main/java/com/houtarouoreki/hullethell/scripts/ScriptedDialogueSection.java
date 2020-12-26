package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;

import java.util.Iterator;

public class ScriptedDialogueSection extends ScriptedSection {
    public ScriptedDialogueSection(World world, ScriptedSectionConfiguration conf, DialogueBox dialogueBox) {
        super(world, conf, dialogueBox);
    }

    @Override
    public void update(double delta) {
        timePassed += delta;

        while (!waitingActions.isEmpty()) {
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
