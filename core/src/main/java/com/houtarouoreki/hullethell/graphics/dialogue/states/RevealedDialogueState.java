package com.houtarouoreki.hullethell.graphics.dialogue.states;

import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueState;

public class RevealedDialogueState extends DialogueState {
    private boolean nextMessageRequested;

    public RevealedDialogueState(DialogueBox dialogueBox) {
        super(dialogueBox);
        setDialogueText(getCurrentMessage().message);
        setCharacterText(getCurrentMessage().character);
    }

    @Override
    protected DialogueState handle() {
        float fullyRevealedDuration = 2;

        if (!nextMessageRequested && getTimeSinceStateChange() < fullyRevealedDuration)
            return this;

        closeMessage();

        if (hasNextMessage())
            return new RevealingDialogueState(dialogueBox);
        else
            return new TimingOutDialogueState(dialogueBox);
    }

    @Override
    public boolean handleSelectControl() {
        nextMessageRequested = true;
        return true;
    }
}
