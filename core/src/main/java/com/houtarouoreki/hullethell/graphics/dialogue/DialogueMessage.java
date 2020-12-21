package com.houtarouoreki.hullethell.graphics.dialogue;

import com.houtarouoreki.hullethell.helpers.BasicListener;

public class DialogueMessage {
    public String character;
    public String message;
    public BasicListener completionListener;

    public DialogueMessage(String character, String message, BasicListener completionListener) {
        this.character = character;
        this.message = message;
        this.completionListener = completionListener;
    }
}
