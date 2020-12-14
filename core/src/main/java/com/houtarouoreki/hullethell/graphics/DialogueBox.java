package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.helpers.BasicListener;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.ui.Label;

import java.util.EnumSet;

public class DialogueBox extends Drawable implements ControlProcessor {
    private final Label label;
    String fullText = "";
    private float timePassed;
    private float maxDuration;
    private BasicListener listener;
    private float appearTime;
    private float disappearTime;
    private float liveTime;

    public DialogueBox() {
        float size = 300;

        setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        setOrigin(new Vector2(0.5f, 1));
        setAnchor(new Vector2(0.5f, 1));
        setSize(new Vector2(1, size));
        setPosition(new Vector2(0, size + 60));

        Rectangle backgroundRect = new Rectangle();
        backgroundRect.setColor(new Color(0.1f, 0.1f, 0.3f, 0.6f));
        backgroundRect.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        add(backgroundRect);

        BorderRectangle fullBorder = new BorderRectangle();
        fullBorder.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        add(fullBorder);

        BorderRectangle avatarBorder = new BorderRectangle();
        avatarBorder.setSize(new Vector2(size, size));
        add(avatarBorder);

        Container labelContainer = new Container();
        labelContainer.setPadding(new PaddingMargin(50, 50, size + 50, 50));
        labelContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        add(labelContainer);

        label = new Label();
        label.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 42;
        label.font = Fonts.getFont("acme", fontParameters);
        labelContainer.add(label);
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        liveTime += delta;
        if (timePassed >= maxDuration) {
            finish();
            return;
        }
        timePassed += delta;
        revealText();
        if (appearTime > disappearTime)
            animatePopIn();
        else if (disappearTime > appearTime)
            animatePopOut();
    }

    private void animatePopIn() {
        float since = liveTime - appearTime;
        float length = 0.5f;
        float a = since / length;
        a = Math.min(a, 1);
        float y = Interpolation.sineOut.apply(getSize().y + 60, 0, a);
        setPosition(new Vector2(0, y));
    }

    private void animatePopOut() {
        float since = liveTime - disappearTime;
        float length = 0.5f;
        float a = since / length;
        a = Math.min(a, 1);
        float y = Interpolation.sineIn.apply(0, getSize().y + 60, a);
        setPosition(new Vector2(0, y));
    }

    private void revealText() {
        float charsPerSecond = 32;
        int shownChars = (int) (timePassed * charsPerSecond);
        String shownText = fullText.substring(0, Math.min(shownChars, fullText.length()));
        label.setText(shownText);
    }

    public void showText(String text, float maxDuration, BasicListener onFinish) {
        if (!text.equals(""))
            appearTime = liveTime;
        else if (!fullText.equals(""))
            disappearTime = liveTime;
        fullText = text;
        timePassed = 0;
        this.maxDuration = maxDuration;
        this.listener = onFinish;
        revealText();
    }

    @Override
    public boolean handleControl(Controls control) {
        if (control == Controls.select) {
            finish();
            return true;
        }
        return false;
    }

    private void finish() {
        if (!fullText.equals(""))
            disappearTime = liveTime;
        if (listener != null)
            listener.onAction();
    }
}
