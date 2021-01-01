package com.houtarouoreki.hullethell.graphics.dialogue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.houtarouoreki.hullethell.graphics.*;
import com.houtarouoreki.hullethell.graphics.dialogue.states.HiddenDialogueState;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.ui.Label;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Queue;

public class DialogueBox extends Drawable implements ControlProcessor {
    private final Queue<DialogueMessage> messages
            = new LinkedList<>();
    private final Label textLabel;
    private final Label characterLabel;
    private DialogueState state;
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
        if (control == Controls.select)
            return state.handleSelectControl();
        return false;
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        state.update(delta);
    }

    public void addMessage(DialogueMessage message) {
        message.character = characterName;
        messages.add(message);
    }

    public void reset() {
        messages.clear();
        setState(new HiddenDialogueState(this));
    }

    void setState(DialogueState state) {
        this.state = state;
    }

    public void setCharacter(String characterName) {
        this.characterName = characterName;
    }

    void closeMessage() {
        DialogueMessage completedMessage = messages.remove();
        completedMessage.completionListener.onAction();
    }

    DialogueMessage getCurrentMessage() {
        return messages.element();
    }

    boolean hasNextMessage() {
        return !messages.isEmpty();
    }

    void setCharacterText(String text) {
        characterLabel.setText(text);
    }

    void setDialogueText(String text) {
        textLabel.setText(text);
    }
}
