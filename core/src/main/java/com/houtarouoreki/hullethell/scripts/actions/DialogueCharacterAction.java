package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;

public class DialogueCharacterAction extends ScriptedAction {
    private final DialogueBox dialogueBox;
    private String characterName;

    public DialogueCharacterAction(DialogueBox dialogueBox) {
        this.dialogueBox = dialogueBox;
    }

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        characterName = arguments.get(0);
    }

    @Override
    protected void performAction() {
        dialogueBox.setCharacter(characterName);
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
