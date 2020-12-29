package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.graphics.*;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptException;
import com.houtarouoreki.hullethell.ui.Label;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import java.util.EnumSet;

public class ScriptErrorScreen extends HulletHellScreen {
    private final Label messageLabel;
    private final Label sorryLabel;
    private final float spacing = 76;
    private final float scale = 1;
    private final Label titleLabel;

    public ScriptErrorScreen() {
        float bottomBarHeight = 40;

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

        Sprite gameLogo = new Sprite();
        gameLogo.texture = HulletHellGame.getAssetManager().get("logo-black-transparent.png");
        gameLogo.setSize(new Vector2(150 * scale));
        gameLogo.setPosition(new Vector2(scale * (spacing - 5) - 6, spacing * 0.56f));
        container.add(gameLogo);

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters
                = new FreeTypeFontGenerator.FreeTypeFontParameter();

        Label controlsLabel = new Label();
        controlsLabel.setAnchor(new Vector2(0, 1));
        controlsLabel.setOrigin(new Vector2(0, 1));
        controlsLabel.font = Fonts.getFont("OpenSans-SemiBold", fontParameters);
        float controlsLabelSpacing = (bottomRectangle.getSize().y + controlsLabel.font.getCapHeight()) * 0.5f;
        controlsLabel.setPosition(new Vector2((spacing - 5) * scale, -controlsLabelSpacing + 2));
        controlsLabel.setText("Press ESCAPE to go back");
        container.add(controlsLabel);

        Container textContainer = new Container();
        container.add(textContainer);
        textContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        textContainer.setPadding(new PaddingMargin((spacing + 120) * scale, scale * spacing + bottomBarHeight, (spacing - 5) * scale, spacing * scale));

        titleLabel = new Label();
        textContainer.add(titleLabel);
        titleLabel.setText("There was a problem executing the stage's script.");
        titleLabel.alignment = Align.topLeft;
        titleLabel.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        fontParameters.size = (int) (16 * scale);
        fontParameters.color = Color.BLACK;
        titleLabel.font = Fonts.getFont("OpenSans-Bold", fontParameters);
        //titleLabel.setPosition(new Vector2(0, gameLogo.getSize().y));

        fontParameters.size = (int) (16 * scale);
        messageLabel = new Label();
        textContainer.add(messageLabel);
        messageLabel.alignment = Align.topLeft;
        messageLabel.font = Fonts.getFont("OpenSans-Regular", fontParameters);
        //messageLabel.setRelativeSizeAxes(EnumSet.allOf(Axes.class));

        sorryLabel = new Label();
        textContainer.add(sorryLabel);
        sorryLabel.font = Fonts.getFont("OpenSans-Bold", fontParameters);
        sorryLabel.setText("We're sorry for the inconvenience.");

        Label signature = new Label();
        signature.setText("HHHHHHHHH");
        signature.font = Fonts.getFont("OpenSans-Bold", fontParameters);
        signature.setOrigin(new Vector2(1));
        signature.setAnchor(new Vector2(1));
        signature.alignment = Align.bottomRight;
        textContainer.add(signature);
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

    @Override
    public void render(GameContainer gc, Graphics g) {
        super.render(gc, g);
        messageLabel.setPosition(new Vector2(0, titleLabel.getPosition().y + titleLabel.lastComputedSize.y + spacing * 0.26f * scale));
        Vector2 temp = messageLabel.getPosition().add(new Vector2(0, messageLabel.lastComputedSize.y));
        temp = temp.add(new Vector2(0, spacing * 0.26f * scale));
        sorryLabel.setPosition(new Vector2(0, temp.y));

        Vector2 trp = titleLabel.getRenderPosition();
        Vector2 trs = titleLabel.lastComputedSize;
        g.fillRect(trp.x, trp.y, trs.x, trs.y);
    }
}
