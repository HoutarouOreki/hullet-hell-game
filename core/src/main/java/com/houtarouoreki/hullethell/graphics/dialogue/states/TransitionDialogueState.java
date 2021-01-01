package com.houtarouoreki.hullethell.graphics.dialogue.states;

import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueState;

public abstract class TransitionDialogueState extends DialogueState {
    public TransitionDialogueState(DialogueBox dialogueBox) {
        super(dialogueBox);
    }

    protected float getTransitionProgress() {
        float a = getTimeSinceStateChange() / 0.4f;
        a = Math.min(1, a);
        a = Math.max(0, a);
        return a;
    }
}
