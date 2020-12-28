package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Fonts;
import com.houtarouoreki.hullethell.graphics.PaddingMargin;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptException;
import com.houtarouoreki.hullethell.ui.Label;

import java.util.EnumSet;

public class ScriptErrorScreen extends HulletHellScreen {
    private final Label messageLabel;

    public ScriptErrorScreen() {
        float spacing = 40;

        container.setPadding(new PaddingMargin(spacing));

        Label titleLabel = new Label();
        container.add(titleLabel);
        titleLabel.setText("There was a problem executing the stage's script.");
        titleLabel.alignment = Align.topLeft;
        titleLabel.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 36;
        titleLabel.font = Fonts.getFont("OpenSans-SemiBold", fontParameters);

        fontParameters.size = 18;
        messageLabel = new Label();
        container.add(messageLabel);
        messageLabel.alignment = Align.topLeft;
        messageLabel.setPosition(new Vector2(0, titleLabel.font.getCapHeight() + spacing));
        messageLabel.font = Fonts.getFont("OpenSans-Regular", fontParameters);
        //messageLabel.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
    }

    @Override
    public int getPreviousScreenId() {
        return LEVEL_SELECT_SCREEN;
    }

    @Override
    public int getId() {
        return SCRIPT_ERROR_SCREEN;
    }

    public void setException(ScriptException e) {
        messageLabel.setText(e.getErrorScreenMessage());
    }
}
