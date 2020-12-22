package com.houtarouoreki.hullethell.graphics.dialogue;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.graphics.*;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.ui.Label;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

public class DialogueBox extends Drawable implements ControlProcessor {
    private final HashMap<Float, Integer> timeTable
            = new HashMap<>();
    private final List<DialogueMessage> messages
            = new ArrayList<>();
    private final Label textLabel;
    private final Label characterLabel;
    private String timeTableIsFor;
    private State state = State.HIDDEN;
    private float stateChangeTime;
    private String characterName;

    public DialogueBox() {
        setSize(new Vector2(1, 200));
        setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        setAnchor(new Vector2(0, 1));

        Container textLabelContainer = new Container();
        add(textLabelContainer);
        textLabelContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        textLabelContainer.setPadding(new PaddingMargin(50, 0, 150, 50));
        textLabelContainer.add(textLabel = new Label());
        textLabel.setRelativeSizeAxes(EnumSet.allOf(Axes.class));

        add(characterLabel = new Label());
        characterLabel.setSize(new Vector2(500, 30));

        Rectangle line = new Rectangle();
        line.setY(40);
        line.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        add(line);
    }

    public void setCharacter(String characterName) {
        this.characterName = characterName;
    }

    public void addMessage(DialogueMessage message) {
        message.character = characterName;
        messages.add(message);
    }

    public void reset() {
        setState(State.HIDDEN);
        timeTableIsFor = "";
        timeTable.clear();
        messages.clear();
    }

    @Override
    public boolean handleControl(Controls control) {
        if (state == State.VISIBLE && control == Controls.select) {
            nextMessage();
            return true;
        }
        return false;
    }

    private void nextMessage() {
        DialogueMessage completedMessage = messages.get(0);
        messages.remove(completedMessage);
        completedMessage.completionListener.onAction();
        setState(messages.isEmpty() ? State.POPPING_OUT : State.VISIBLE);
    }

    private void setState(State state) {
        this.state = state;
        stateChangeTime = getTime();
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        switch (state) {
            case HIDDEN:
                handleHidden();
                break;
            case POPPING_IN:
                handlePopIn();
                break;
            case VISIBLE:
                handleVisible();
                break;
            case POPPING_OUT:
                handlePopOut();
                break;
        }
    }

    private float getTimeSinceStateChange() {
        return getTime() - stateChangeTime;
    }

    private float getTransitionProgress() {
        float a = getTimeSinceStateChange();
        a = Math.min(1, a);
        a = Math.max(0, a);
        return a;
    }

    private void handleHidden() {
        if (!messages.isEmpty())
            setState(State.POPPING_IN);
    }

    private void handlePopIn() {
        float a = getTransitionProgress();
        setY(Interpolation.sineOut.apply(0, -getSize().y, a));
        if (a == 1)
            setState(State.VISIBLE);
    }

    private void handlePopOut() {
        float a = getTransitionProgress();
        setY(Interpolation.sineIn.apply(-getSize().y, 0, a));
        if (a == 1)
            setState(State.HIDDEN);
    }

    private float getCharacterDuration(char c) {
        if (c == '.')
            return 0.7f;
        if (c == ',')
            return 0.4f;
        return 0.02f;
    }

    private void generateTimeTable() {
        timeTable.clear();
        float totalTime = 0;
        String message = messages.get(0).message;
        for (int i = 0; i < message.length(); i++) {
            timeTable.put(totalTime, i);
            totalTime += getCharacterDuration(message.charAt(i));
        }
        timeTable.put(totalTime + 2, -1);
        timeTableIsFor = message;
    }

    private void handleVisible() {
        String message = messages.get(0).message;
        characterLabel.setText(messages.get(0).character);
        if (timeTableIsFor == null || !timeTableIsFor.equals(message))
            generateTimeTable();
        float maxTime = 0;
        for (Float time : timeTable.keySet()) {
            if (getTimeSinceStateChange() > time)
                maxTime = Math.max(maxTime, time); // idk if necessary
        }
        int lastIndex = timeTable.get(maxTime);
        if (lastIndex == -1) {
            nextMessage();
            return;
        }
        textLabel.setText(message.substring(0, lastIndex + 1));
    }

    private enum State {
        HIDDEN,
        POPPING_IN,
        VISIBLE,
        POPPING_OUT
    }
}
