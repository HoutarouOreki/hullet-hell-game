package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueMessage;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class DialogueTextAction extends ScriptedAction {
    private final DialogueBox dialogueBox;
    private String text;

    public DialogueTextAction(DialogueBox dialogueBox) {
        this.dialogueBox = dialogueBox;
    }

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        if (arguments.get(0) != null)
            text = arguments.get(0).replace(",\\", ",");
        else
            text = "";
    }

    @Override
    protected void performAction() {
        if (getTicks() != 0)
            return;
        dialogueBox.addMessage(new DialogueMessage("Temp", text, this::setFinished));
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
