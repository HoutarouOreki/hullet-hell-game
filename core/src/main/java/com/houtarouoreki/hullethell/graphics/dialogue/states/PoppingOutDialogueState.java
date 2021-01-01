package com.houtarouoreki.hullethell.graphics.dialogue.states;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueState;

public class PoppingOutDialogueState extends TransitionDialogueState {
    public PoppingOutDialogueState(DialogueBox dialogueBox) {
        super(dialogueBox);
    }

    @Override
    protected DialogueState handle() {
        float a = getTransitionProgress();
        dialogueBox.setY(Interpolation.sineIn.apply(-dialogueBox.getSize().y, 0, a));
        if (a == 1)
            return new HiddenDialogueState(dialogueBox);
        return this;
    }

    @Override
    public boolean handleSelectControl() {
        return false;
    }
}
