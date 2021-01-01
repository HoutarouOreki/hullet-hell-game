package com.houtarouoreki.hullethell.graphics.dialogue.states;

import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueState;

import java.util.HashMap;

public class RevealingDialogueState extends DialogueState {
    private final HashMap<Float, Integer> timeTable = new HashMap<>();
    private boolean fullRevealRequested = false;

    public RevealingDialogueState(DialogueBox dialogueBox) {
        super(dialogueBox);
        generateTimeTable();
    }

    private void generateTimeTable() {
        timeTable.clear();
        float totalTime = 0;
        String message = getCurrentMessage().message;
        for (int i = 0; i < message.length(); i++) {
            timeTable.put(totalTime, i);
            totalTime += getCharacterDuration(message.charAt(i));
        }
    }

    private float getCharacterDuration(char c) {
        switch (c) {
            case '.':
            case '?':
            case '!':
                return 0.7f;
            case ',':
                return 0.4f;
            default:
                return 0.02f;
        }
    }

    @Override
    protected DialogueState handle() {
        if (fullRevealRequested)
            return new RevealedDialogueState(dialogueBox);
        String message = getCurrentMessage().message;
        setCharacterText(getCurrentMessage().character);
        float maxTime = 0;
        for (Float time : timeTable.keySet()) {
            if (getTimeSinceStateChange() > time)
                // idk if necessary, but I think keySet doesn't ensure order
                maxTime = Math.max(maxTime, time);
        }
        int lastIndex = timeTable.get(maxTime);
        setDialogueText(message.substring(0, lastIndex + 1));
        if (lastIndex + 1 == message.length())
            return new RevealedDialogueState(dialogueBox);
        return this;
    }

    @Override
    public boolean handleSelectControl() {
        fullRevealRequested = true;
        return true;
    }
}
