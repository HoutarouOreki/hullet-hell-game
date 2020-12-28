package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueMessage;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionStringArg;

public class DialogueTextAction extends ScriptedAction {
    private final DialogueBox dialogueBox;
    private String text;

    public DialogueTextAction(DialogueBox dialogueBox) {
        this.dialogueBox = dialogueBox;
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

    @Override
    protected void addArgumentsInfo() {
        parser.stringArgs.add(new ActionStringArg(
                "Text",
                null,
                "Aren't circles clickable?",
                match_everything_pattern,
                this::setText,
                false
        ));
    }

    private void setText(String text) {
        this.text = text.replace(",\\", ",");
    }
}
