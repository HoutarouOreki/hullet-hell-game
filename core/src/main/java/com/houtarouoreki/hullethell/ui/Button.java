package com.houtarouoreki.hullethell.ui;

import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Rectangle;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.numbers.Vector2;

import java.util.EnumSet;

public class Button extends MenuComponent {
    private final Rectangle background;
    private final Label label;
    public ButtonListener listener;

    public Button() {
        EnumSet<Axes> bothAxes = EnumSet.of(Axes.HORIZONTAL, Axes.VERTICAL);
        background = new Rectangle();
        background.setRelativePositionAxes(bothAxes);
        background.setRelativeSizeAxes(bothAxes);
        background.setSize(new Vector2(1));
        background.setColor(UNACTIVE_COLOR);
        add(background);

        label = new Label();
        label.setRelativePositionAxes(bothAxes);
        label.setRelativeSizeAxes(bothAxes);
        label.setSize(new Vector2(1));
        label.alignment = Align.center;
        label.setOrigin(new Vector2(0.5f));
        label.setAnchor(new Vector2(0.5f));
        add(label);
    }

    @Override
    public boolean handleControl(Controls control) {
        if (control == Controls.select && listener != null) {
            listener.onAction();
            playSound();
            return true;
        }
        return false;
    }

    private void playSound() {
        HulletHellGame.getSoundManager().playSound("button1", 0.5f);
    }

    @Override
    protected void onFocused() {
        super.onFocused();
        background.setColor(ACTIVE_COLOR);
    }

    @Override
    protected void onFocusLost() {
        super.onFocusLost();
        background.setColor(UNACTIVE_COLOR);
    }

    public String getText() {
        return label.getText();
    }

    public void setText(String text) {
        label.setText(text);
    }

    public interface ButtonListener {
        void onAction();
    }
}
