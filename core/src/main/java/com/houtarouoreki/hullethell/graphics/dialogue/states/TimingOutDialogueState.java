package com.houtarouoreki.hullethell.graphics.dialogue.states;

import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueState;

public class TimingOutDialogueState extends DialogueState {
    public TimingOutDialogueState(DialogueBox dialogueBox) {
        super(dialogueBox);
    }

    @Override
    protected DialogueState handle() {
        if (getTimeSinceStateChange() > 0.1f)
            return new PoppingOutDialogueState(dialogueBox);
        else if (hasNextMessage())
            return new RevealingDialogueState(dialogueBox);
        return this;
    }

    @Override
    public boolean handleSelectControl() {
        return false;
    }
}
