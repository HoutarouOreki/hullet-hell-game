package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.graphics.DialogueBox;
import com.houtarouoreki.hullethell.helpers.BasicListener;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class DialogueAction extends ScriptedAction {
    private final DialogueBox dialogueBox;
    private String text;
    private float maxDuration;

    public DialogueAction(DialogueBox dialogueBox) {
        this.dialogueBox = dialogueBox;
    }

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        if (arguments.get(0) != null)
            text = arguments.get(0).replace(",\\", ",");
        else
            text = "";
        maxDuration = arguments.get(1).isEmpty() ?
                Float.POSITIVE_INFINITY : Float.parseFloat(arguments.get(1));
    }

    @Override
    protected void performAction() {
        if (getTicks() != 0)
            return;
        dialogueBox.showText(text, maxDuration, new BasicListener() {
            @Override
            public void onAction() {
                setFinished();
            }
        });
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
