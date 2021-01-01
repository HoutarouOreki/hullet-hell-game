package com.houtarouoreki.hullethell.graphics.dialogue.states;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueState;

public class PoppingInDialogueState extends TransitionDialogueState {
    public PoppingInDialogueState(DialogueBox dialogueBox) {
        super(dialogueBox);
        dialogueBox.setVisibility(true);
    }

    @Override
    protected DialogueState handle() {
        float a = getTransitionProgress();
        dialogueBox.setY(Interpolation.sineOut.apply(0, -dialogueBox.getSize().y, a));
        if (a == 1)
            return new RevealingDialogueState(dialogueBox);
        return this;
    }

    @Override
    public boolean handleSelectControl() {
        return false;
    }
}
