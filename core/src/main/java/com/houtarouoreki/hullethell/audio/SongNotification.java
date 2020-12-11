package com.houtarouoreki.hullethell.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Drawable;
import com.houtarouoreki.hullethell.graphics.Sprite;
import com.houtarouoreki.hullethell.ui.Label;

import java.util.EnumSet;

public class SongNotification extends Drawable {
    private final float length = 5;
    private final AssetManager assetManager;
    private final Sprite sprite;
    private final float width = 300;
    private final Label titleLabel;
    private final Label artistLabel;
    private float timeLeft = length;
    private SongConfiguration song;

    public SongNotification(AssetManager assetManager) {
        this.assetManager = assetManager;
        setAnchor(new Vector2(1, 1));
        setOrigin(new Vector2(0, 1));
        setSize(new Vector2(width, 80));
        add(sprite = new Sprite());
        sprite.setRelativeSizeAxes(EnumSet.allOf(Axes.class));

        titleLabel = new Label();
        titleLabel.setPosition(new Vector2(90, 24));
        titleLabel.setColor(new Color(0, 0, 143 / 255f, 1));
        add(titleLabel);

        artistLabel = new Label();
        artistLabel.setPosition(new Vector2(90, 42));
        artistLabel.setColor(new Color(15 / 255f, 15 / 255f,
                96 / 255f, 1));
        add(artistLabel);
    }

    public void onUpdate(float delta) {
        timeLeft -= delta;
        if (sprite.texture == null && assetManager
                .isLoaded("ui/songNotification.png"))
            sprite.texture = assetManager.get("ui/songNotification.png");

        if (song == null || timeLeft < 0)
            return;

        float left = 0;
        float fadeLength = 1;

        float nonFadeLength = length - fadeLength;
        if (timeLeft > length - fadeLength) {
            float a = 1 - ((timeLeft - nonFadeLength) / fadeLength);
            left += Interpolation.sineIn.apply(width + 30, 0, a);
        } else if (timeLeft < fadeLength) {
            float a = 1 - (timeLeft / fadeLength);
            left += Interpolation.sineIn.apply(0, width + 30, a);
        }
        setPosition(new Vector2(left, 0));
    }

    public void show(SongConfiguration song) {
        timeLeft = length;
        this.song = song;
        titleLabel.setText(song.title);
        artistLabel.setText(song.author);
    }
}
