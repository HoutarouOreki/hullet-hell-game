package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedActionInitializationException;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedSectionInitializationException;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedSectionUpdateException;

import java.util.Iterator;

public class ScriptedDialogueSection extends ScriptedSection {
    public ScriptedDialogueSection(World world, ScriptedSectionConfiguration conf, DialogueBox dialogueBox) throws ScriptedSectionInitializationException {
        super(world, conf, dialogueBox);
    }

    @Override
    public void update(double delta) throws ScriptedSectionUpdateException {
        timePassed += delta;

        while (!waitingActions.isEmpty()) {
            ScriptedAction action = waitingActions.remove();
            try {
                action.initialise(world, this, null);
            } catch (ScriptedActionInitializationException e) {
                throw new ScriptedSectionUpdateException(this, e);
            }
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
