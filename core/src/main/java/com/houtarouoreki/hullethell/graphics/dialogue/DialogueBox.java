package com.houtarouoreki.hullethell.graphics.dialogue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.graphics.*;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.ui.Label;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class DialogueBox extends Drawable implements ControlProcessor {
    private final HashMap<Float, Integer> timeTable
            = new HashMap<>();
    private final Queue<DialogueMessage> messages
            = new LinkedList<>();
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

        FreeTypeFontGenerator.FreeTypeFontParameter fontParams
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams.size = 30;
        fontParams.borderWidth = 1;
        fontParams.borderColor = Color.BLACK;
        characterLabel.font = Fonts.getFont("acme", fontParams);

        fontParams.size = 20;
        fontParams.borderWidth = 0.4f;
        textLabel.font = Fonts.getFont("acme", fontParams);

        Rectangle line = new Rectangle();
        line.setY(40);
        line.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        add(line);
    }

    @Override
    public boolean handleControl(Controls control) {
        if (control != Controls.select)
            return false;
        if (state == State.REVEALED) {
            nextMessage();
            return true;
        } else if (state == State.REVEALING) {
            setState(State.REVEALED);
            return true;
        }
        return false;
    }

    private void nextMessage() {
        DialogueMessage completedMessage = messages.remove();
        completedMessage.completionListener.onAction();
        setState(messages.isEmpty() ? State.TIMING_OUT : State.REVEALING);
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
            case REVEALING:
                handleRevealing();
                break;
            case REVEALED:
                handleRevealed();
                break;
            case TIMING_OUT:
                handleTimingOut();
                break;
            case POPPING_OUT:
                handlePopOut();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void addMessage(DialogueMessage message) {
        message.character = characterName;
        messages.add(message);
    }

    public State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
        stateChangeTime = getTime();
        setVisibility(state != State.HIDDEN);
    }

    public void reset() {
        setState(State.HIDDEN);
        timeTableIsFor = "";
        timeTable.clear();
        messages.clear();
    }

    public void setCharacter(String characterName) {
        this.characterName = characterName;
    }

    private void generateTimeTable() {
        timeTable.clear();
        float totalTime = 0;
        String message = messages.element().message;
        for (int i = 0; i < message.length(); i++) {
            timeTable.put(totalTime, i);
            totalTime += getCharacterDuration(message.charAt(i));
        }
        timeTableIsFor = message;
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

    private float getTimeSinceStateChange() {
        return getTime() - stateChangeTime;
    }

    private float getTransitionProgress() {
        float a = getTimeSinceStateChange() / 0.4f;
        a = Math.min(1, a);
        a = Math.max(0, a);
        return a;
    }

    private void handleHidden() {
        characterLabel.setText("");
        textLabel.setText("");
        if (!messages.isEmpty())
            setState(State.POPPING_IN);
    }

    private void handlePopIn() {
        float a = getTransitionProgress();
        setY(Interpolation.sineOut.apply(0, -getSize().y, a));
        if (a == 1)
            setState(State.REVEALING);
    }

    private void handlePopOut() {
        float a = getTransitionProgress();
        setY(Interpolation.sineIn.apply(-getSize().y, 0, a));
        if (a == 1)
            setState(State.HIDDEN);
    }

    private void handleRevealed() {
        float fullyRevealedDuration = 2;
        characterLabel.setText(messages.element().character);
        textLabel.setText(messages.element().message);
        if (getTimeSinceStateChange() >= fullyRevealedDuration)
            nextMessage();
    }

    private void handleRevealing() {
        String message = messages.element().message;
        characterLabel.setText(messages.element().character);
        if (timeTableIsFor == null || !timeTableIsFor.equals(message))
            generateTimeTable();
        float maxTime = 0;
        for (Float time : timeTable.keySet()) {
            if (getTimeSinceStateChange() > time)
                // idk if necessary, but I think keySet doesn't ensure order
                maxTime = Math.max(maxTime, time);
        }
        int lastIndex = timeTable.get(maxTime);
        textLabel.setText(message.substring(0, lastIndex + 1));
        if (lastIndex + 1 == message.length())
            setState(State.REVEALED);
    }

    private void handleTimingOut() {
        if (getTimeSinceStateChange() > 0.1f)
            setState(State.POPPING_OUT);
        else if (!messages.isEmpty())
            setState(State.REVEALING);
    }

    public enum State {
        HIDDEN,
        POPPING_IN,
        REVEALING,
        REVEALED,
        TIMING_OUT,
        POPPING_OUT
    }
}
