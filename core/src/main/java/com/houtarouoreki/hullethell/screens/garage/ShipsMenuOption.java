package com.houtarouoreki.hullethell.screens.garage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ShipConfiguration;
import com.houtarouoreki.hullethell.graphics.*;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.ui.Label;
import com.houtarouoreki.hullethell.ui.MenuComponent;

import java.util.Collection;
import java.util.EnumSet;

public class ShipsMenuOption extends MenuComponent {
    private static final float height = 64;
    private static final float spacing = 6;
    private static final float spriteContainerHeight = height - 2 * spacing;
    private static final float spriteContainerWidth = spriteContainerHeight + 40;
    private static final Vector2 spriteContainerSize = new Vector2(spriteContainerWidth, spriteContainerHeight);
    public static final Vector2 spriteContainerContentSize = spriteContainerSize.sub(new Vector2(spacing * 2));
    public final ShipConfiguration shipConfiguration;
    private final Rectangle background;

    public ShipsMenuOption(ShipConfiguration shipConfiguration, Collection<ShipConfiguration> allOther) {
        this.shipConfiguration = shipConfiguration;

        setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        setSize(new Vector2(1, height));

        add(background = new Rectangle());
        background.setRelativeSizeAxes(EnumSet.allOf(Axes.class));

        Container container = new Container();
        container.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        add(container);
        container.setPadding(new PaddingMargin(spacing));

        Rectangle spriteBackground = new Rectangle();
        container.add(spriteBackground);
        spriteBackground.setSize(spriteContainerSize);
        spriteBackground.setColor(Color.BLACK);

        Container spriteContainer = new Container();
        container.add(spriteContainer);
        spriteContainer.setSize(spriteContainerSize);
        spriteContainer.setPadding(new PaddingMargin(spacing));

        float spriteDownscale = GarageScreen.getScale(allOther, spriteContainer.getContentRenderSize());

        Sprite shipSprite = new Sprite();
        shipSprite.texture = HulletHellGame.getAssetManager().get(shipConfiguration.path + ".png");
        shipSprite.setAnchor(new Vector2(0.5f));
        shipSprite.setOrigin(new Vector2(0.5f));
        shipSprite.setSize(shipConfiguration.size.scl(spriteDownscale));
        spriteContainer.add(shipSprite);

        Container textContainer = new Container();
        textContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        textContainer.setPadding(new PaddingMargin(spacing, spacing,
                spriteContainerWidth + spacing, spacing));
        container.add(textContainer);

        Label shipNameLabel = new Label();
        textContainer.add(shipNameLabel);
        shipNameLabel.setText(shipConfiguration.name);
        shipNameLabel.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        shipNameLabel.alignment = Align.center;
        shipNameLabel.setAnchor(new Vector2(0.5f));
        shipNameLabel.setOrigin(new Vector2(0.5f));

        unfocus();
    }

    @Override
    protected void onFocused() {
        background.setColor(ACTIVE_COLOR);
        super.onFocused();
    }

    @Override
    protected void onFocusLost() {
        background.setColor(UNACTIVE_COLOR);
        super.onFocusLost();
    }

    @Override
    public boolean handleControl(Controls control) {
        return false;
    }
}
