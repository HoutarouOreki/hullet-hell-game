package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionStringArg;

public class DialogueCharacterAction extends ScriptedAction {
    private final DialogueBox dialogueBox;
    private String characterName;

    public DialogueCharacterAction(DialogueBox dialogueBox) {
        this.dialogueBox = dialogueBox;
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

    @Override
    protected void addArgumentsInfo() {
        parser.stringArgs.add(new ActionStringArg(
                "Person's name",
                null,
                "Lieutenant George",
                match_everything_pattern,
                this::setCharacterName,
                false
        ));
    }

    private void setCharacterName(String s) {
        characterName = s;
    }
}
