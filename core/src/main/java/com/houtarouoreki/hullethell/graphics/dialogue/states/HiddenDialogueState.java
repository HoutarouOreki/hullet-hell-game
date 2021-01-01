package com.houtarouoreki.hullethell.graphics.dialogue.states;

import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueState;

public class HiddenDialogueState extends DialogueState {
    public HiddenDialogueState(DialogueBox dialogueBox) {
        super(dialogueBox);
        setCharacterText("");
        setDialogueText("");
        dialogueBox.setVisibility(false);
    }

    @Override
    protected DialogueState handle() {
        if (hasNextMessage())
            return new PoppingInDialogueState(dialogueBox);
        return this;
    }

    @Override
    public boolean handleSelectControl() {
        return false;
    }
}
