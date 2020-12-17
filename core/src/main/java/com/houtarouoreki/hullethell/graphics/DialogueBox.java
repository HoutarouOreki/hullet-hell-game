package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.bindables.BindableNumber;
import com.houtarouoreki.hullethell.helpers.BasicListener;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.ui.Label;

import java.util.EnumSet;

public class DialogueBox extends Drawable implements ControlProcessor {
    private final Label text;
    private final float size;
    private final Sprite characterSprite;
    private final Label characterName;
    private final float characterX = 100;
    private final float transitionsLength = 1;
    String fullText = "";
    private float timePassed;
    private float maxDuration;
    private BasicListener listener;
    private float appearTime;
    private float disappearTime;
    private float liveTime;

    public DialogueBox() {
        size = 200;

        setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        setOrigin(new Vector2(0.5f, 1));
        setAnchor(new Vector2(0.5f, 1));
        setSize(new Vector2(1, size));
        setPosition(new Vector2(0, size + 60));

        Sprite background = new Sprite();
        background.texture = HulletHellGame.getAssetManager().get("ui/dialogueOverlay.png");
        background.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        add(background);

        Container textContainer = new Container();
        textContainer.setPadding(new PaddingMargin(
                size * 0.46f, 50, 150, 100));
        textContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        add(textContainer);

        text = new Label();
        text.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 26;
        text.font = Fonts.getFont("acme", fontParameters);
        textContainer.add(text);

        Container characterNameContainer = new Container();
        characterNameContainer.setPosition(new Vector2(150, 32));
        characterNameContainer.setSize(new Vector2(500, 50));
        add(characterNameContainer);

        characterName = new Label();
        fontParameters.size = 34;
        fontParameters.borderWidth = 1;
        fontParameters.borderColor = Color.BLACK;
        characterName.font = Fonts.getFont("acme", fontParameters);
        characterNameContainer.add(characterName);

        characterSprite = new Sprite();
        characterSprite.setSize(new Vector2(160, 160));
        characterSprite.setOrigin(new Vector2(0, 1));
        characterSprite.setPosition(new Vector2(characterX, 0));
        add(characterSprite);
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
        BindableNumber<Float> a
                = new BindableNumber<Float>(since / transitionsLength, 0f, 1f);
        BindableNumber<Float> vertical
                = new BindableNumber<Float>(a.getValue() * 2, 0f, 1f);
        BindableNumber<Float> horizontal
                = new BindableNumber<Float>(a.getValue() * 2 - 1, 0f, 1f);
        float y = Interpolation.sineOut
                .apply(getSize().y + 60, 0, vertical.getValue());
        setPosition(new Vector2(0, y));
        float x = Interpolation.sineOut
                .apply(-characterSprite.getSize().x, characterX, horizontal.getValue());
        characterSprite.setPosition(new Vector2(x, 0));
    }

    private void animatePopOut() {
        float since = liveTime - disappearTime;
        BindableNumber<Float> a
                = new BindableNumber<Float>(since / transitionsLength, 0f, 1f);
        BindableNumber<Float> vertical
                = new BindableNumber<Float>(a.getValue() * 2 - 1, 0f, 1f);
        BindableNumber<Float> horizontal
                = new BindableNumber<Float>(a.getValue() * 2, 0f, 1f);
        float y = Interpolation.sineIn
                .apply(0, getSize().y + 60, vertical.getValue());
        setPosition(new Vector2(0, y));
        float x = Interpolation.sineIn
                .apply(characterX, -characterSprite.getSize().x, horizontal.getValue());
        characterSprite.setPosition(new Vector2(x, 0));
    }

    private void revealText() {
        float charsPerSecond = 32;
        int shownChars = (int) (timePassed * charsPerSecond);
        String shownText = fullText.substring(0, Math.min(shownChars, fullText.length()));
        text.setText(shownText);
    }

    public void setCharacter(String name) {
        if (name == null || name.equals("")) {
            characterSprite.setVisibility(false);
            characterName.setVisibility(false);
        } else {
            characterSprite.setVisibility(true);
            characterName.setVisibility(true);
            characterName.setText(name);
            characterSprite.texture = HulletHellGame.getAssetManager()
                    .get("characters/" + name + ".png");
        }
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

    public void reset() {
        appearTime = 0;
        disappearTime = 0;
        fullText = "";
        liveTime = 0;
        characterSprite.setPosition(new Vector2(-characterSprite.getSize().x, 0));
        setPosition(new Vector2(0, size + 60));
        setCharacter(null);
    }
}
