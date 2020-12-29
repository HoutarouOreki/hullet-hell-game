package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.graphics.*;
import com.houtarouoreki.hullethell.graphics.flow.FlowContainer;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptException;
import com.houtarouoreki.hullethell.ui.Label;

import java.util.EnumSet;

public class ScriptErrorScreen extends HulletHellScreen {
    private final float spacing = 76;
    private final float scale = 1;
    float bottomBarHeight = 40;
    private Label messageLabel;

    public ScriptErrorScreen() {
        createBackground();
        createMainFlow();

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters
                = new FreeTypeFontGenerator.FreeTypeFontParameter();

        Container controlsLabelContainer = new Container();
        controlsLabelContainer.setAnchor(new Vector2(0, 1));
        controlsLabelContainer.setOrigin(new Vector2(0, 1));
        controlsLabelContainer.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        controlsLabelContainer.setSize(new Vector2(1, bottomBarHeight));
        container.add(controlsLabelContainer);

        Label controlsLabel = new Label();
        controlsLabel.setOrigin(new Vector2(0, 0.5f));
        controlsLabel.font = Fonts.getFont("OpenSans-SemiBold", fontParameters);
        controlsLabel.setRelativePositionAxes(EnumSet.of(Axes.VERTICAL));
        controlsLabel.setPosition(new Vector2((spacing - 5) * scale, 0.5f));
        controlsLabel.setText("Press ESCAPE to go back");
        controlsLabelContainer.add(controlsLabel);
    }

    private void createBackground() {
        Rectangle background = new Rectangle();
        background.setColor(Color.valueOf("FEFE00"));
        background.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        container.add(background);

        Rectangle background2 = new Rectangle();
        background2.setColor(Color.valueOf("FAF400"));
        background2.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        background2.setRelativePositionAxes(EnumSet.allOf(Axes.class));
        background2.setSize(new Vector2(0.4f, 0.4f));
        background2.setAnchor(new Vector2(1));
        background2.setOrigin(new Vector2(1));
        background2.setPosition(new Vector2(-0.12f, 0));
        container.add(background2);

        Rectangle background3 = new Rectangle();
        background3.setColor(Color.valueOf("FAF400"));
        background3.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        background3.setSize(new Vector2(0.2f, 0.3f));
        background3.setPosition(new Vector2(spacing * scale / 4, spacing * scale * 1.1f));
        container.add(background3);

        BorderRectangle borderRectangle = new BorderRectangle();
        borderRectangle.thickness = 1;
        borderRectangle.setColor(Color.valueOf("A5A300"));
        borderRectangle.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        borderRectangle.setMargin(new PaddingMargin(spacing * scale / 3f));
        container.add(borderRectangle);

        Rectangle bottomRectangle = new Rectangle();
        bottomRectangle.setColor(Color.BLACK);
        bottomRectangle.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        bottomRectangle.setAnchor(new Vector2(1, 1));
        bottomRectangle.setOrigin(new Vector2(1, 1));
        bottomRectangle.setSize(new Vector2(1, bottomBarHeight));
        container.add(bottomRectangle);

        Rectangle bottomLine = new Rectangle();
        bottomLine.setColor(Color.valueOf("05FDFF"));
        bottomLine.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        bottomLine.setAnchor(new Vector2(1, 1));
        bottomLine.setOrigin(new Vector2(1, 1));
        bottomLine.setSize(new Vector2(1, 1));
        bottomLine.setPosition(new Vector2(0, -bottomBarHeight));
        container.add(bottomLine);
    }

    private void createMainFlow() {
        FlowContainer flowContainer = new FlowContainer(Axes.VERTICAL);
        flowContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        container.add(flowContainer);

        Sprite gameLogo = new Sprite();
        gameLogo.texture = HulletHellGame.getAssetManager().get("logo-black-transparent.png");
        gameLogo.widthHeightRatioForFitFill = gameLogo.texture.getWidth() / (float)gameLogo.texture.getHeight();
        gameLogo.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        gameLogo.setMargin(new PaddingMargin(0, -30));
        gameLogo.setFillMode(FillMode.FIT);

        Container gameLogoContainer = new Container();
        gameLogoContainer.add(gameLogo);
        gameLogoContainer.setSize(new Vector2(500, 150));
        flowContainer.addFlowItem(gameLogoContainer);

        flowContainer.setPadding(new PaddingMargin(spacing * scale,
                scale * spacing + bottomBarHeight,
                (spacing - 5) * scale,
                spacing * scale));

        FlowContainer textContainer = new FlowContainer(Axes.VERTICAL);
        textContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        textContainer.spacing = 30;
        flowContainer.addFlowItem(textContainer);

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters
                = new FreeTypeFontGenerator.FreeTypeFontParameter();

        Label titleLabel = new Label();
        titleLabel.setText("There was a problem executing the stage's script.");
        titleLabel.alignment = Align.topLeft;
        titleLabel.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        fontParameters.size = (int) (16 * scale);
        fontParameters.color = Color.BLACK;
        titleLabel.font = Fonts.getFont("OpenSans-Bold", fontParameters);
        textContainer.addFlowItem(titleLabel);

        fontParameters.size = (int) (16 * scale);
        messageLabel = new Label();
        messageLabel.alignment = Align.topLeft;
        messageLabel.font = Fonts.getFont("OpenSans-Regular", fontParameters);
        textContainer.addFlowItem(messageLabel);

        Label sorryLabel = new Label();
        sorryLabel.font = Fonts.getFont("OpenSans-Bold", fontParameters);
        sorryLabel.setText("We're sorry for the inconvenience.");
        textContainer.addFlowItem(sorryLabel);

        Label signature = new Label();
        signature.setText("HHHHHHHHH");
        signature.font = Fonts.getFont("OpenSans-Bold", fontParameters);
        signature.setOrigin(new Vector2(1));
        signature.setAnchor(new Vector2(1));
        signature.alignment = Align.bottomRight;
        flowContainer.addNonFlowItem(signature);
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
