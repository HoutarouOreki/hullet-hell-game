package com.houtarouoreki.hullethell.graphics.dialogue;

import com.houtarouoreki.hullethell.environment.Updatable;

public abstract class DialogueState implements Updatable {
    protected final DialogueBox dialogueBox;
    private float time;

    public DialogueState(DialogueBox dialogueBox) {
        this.dialogueBox = dialogueBox;
    }

    public final void update(float delta) {
        time += delta;
        dialogueBox.setState(handle());
    }

    protected abstract DialogueState handle();

    public float getTimeSinceStateChange() {
        return time;
    }

    public abstract boolean handleSelectControl();

    protected void closeMessage() {
        dialogueBox.closeMessage();
    }

    protected DialogueMessage getCurrentMessage() {
        return dialogueBox.getCurrentMessage();
    }

    protected boolean hasNextMessage() {
        return dialogueBox.hasNextMessage();
    }

    protected void setCharacterText(String text) {
        dialogueBox.setCharacterText(text);
    }

    protected void setDialogueText(String text) {
        dialogueBox.setDialogueText(text);
    }
}
