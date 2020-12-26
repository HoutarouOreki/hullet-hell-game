package com.houtarouoreki.hullethell.screens.garage;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;
import com.houtarouoreki.hullethell.configurations.ShipConfiguration;
import com.houtarouoreki.hullethell.graphics.*;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.screens.HulletHellScreen;
import com.houtarouoreki.hullethell.ui.Label;
import org.mini2Dx.core.screen.Transition;

import java.util.Collection;
import java.util.EnumSet;

public class GarageScreen extends HulletHellScreen {
    private final ShipsMenu menu;
    private final Container shipDisplayContainer;
    private final Label shipInfoLabel;

    public GarageScreen() {
        Container menuContainer = new Container();
        container.add(menuContainer);
        menuContainer.add(menu = new ShipsMenu());
        menuContainer.setPadding(new PaddingMargin(5));
        menu.setAnchor(new Vector2(0.5f));
        menu.setOrigin(new Vector2(0.5f));
        menu.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        menuContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        float menuRelativeWidth = 0.3f;
        menuContainer.setSize(new Vector2(menuRelativeWidth, 1));

        container.add(shipDisplayContainer = new Container());
        float shipDisplayRelativeHeight = 0.6f;
        shipDisplayContainer.setSize(new Vector2(1 - menuRelativeWidth, shipDisplayRelativeHeight));
        shipDisplayContainer.setAnchor(new Vector2(menuRelativeWidth, 0));
        shipDisplayContainer.setRelativePositionAxes(EnumSet.allOf(Axes.class));
        shipDisplayContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));

        Container shipInfoContainer = new Container();
        container.add(shipInfoContainer);
        shipInfoContainer.add(shipInfoLabel = new Label());
        shipInfoContainer.setAnchor(new Vector2(1));
        shipInfoContainer.setOrigin(new Vector2(1));
        shipInfoContainer.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        shipInfoContainer.setSize(new Vector2(1 - menuRelativeWidth, 1 - shipDisplayRelativeHeight));
        shipInfoContainer.setPadding(new PaddingMargin(20));

        HulletHellGame.getPlayerState().currentShipFileName
                .addListener(this::onShipChanged, true);
    }

    public static float getScale(Collection<ShipConfiguration> shipConfigurations, Vector2 fitInto) {
        float minScaleY = 500000;
        float minScaleX = 500000;
        for (ShipConfiguration c : shipConfigurations) {
            minScaleY = Math.min(minScaleY, getXScaleToFit(c.size, fitInto));
            minScaleX = Math.min(minScaleX, getYScaleToFit(c.size, fitInto));
        }
        return Math.min(minScaleX, minScaleY);
    }

    public static float getXScaleToFit(Vector2 size, Vector2 fitInto) {
        return size.fit(fitInto).x / size.x;
    }

    public static float getYScaleToFit(Vector2 size, Vector2 fitInto) {
        return size.fit(fitInto).y / size.y;
    }

    private void onShipChanged(String oldValue, String newValue) {
        shipDisplayContainer.children.clear();

        ShipConfiguration c = HulletHellGame.getPlayerState().getCurrentShipConfiguration();
        ShipSprite shipSprite = new ShipSprite(c);
        shipSprite.texture = HulletHellGame.getAssetManager().get(c.path + ".png");
        shipSprite.setAnchor(new Vector2(0.5f));
        shipSprite.setOrigin(new Vector2(0.5f));
        shipSprite.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        shipSprite.widthHeightRatioForFitFill = c.size.getWidthHeightRatio();
        shipSprite.setFillMode(FillMode.FIT);
        shipSprite.setSize(new Vector2(0.5f));
        shipDisplayContainer.add(shipSprite);

        shipInfoLabel.setText(getShipDescription());
    }

    private String getShipDescription() {
        ShipConfiguration c = HulletHellGame.getPlayerState().getCurrentShipConfiguration();
        BodyConfiguration b = BodyConfiguration.fromPath("bullets/" + c.ammunitionName);
        float bps = 1 / c.cannonTimeOut;
        float dps = bps * b.damage;
        return "Durability: " + roundNumber(c.maxHealth) + '\n' +
                "Bullets per second: " + roundNumber(bps) + '\n' +
                "Bullet speed: " +  roundNumber(c.ammunitionSpeed) + '\n' +
                "Bullet damage: " + roundNumber(b.damage) + '\n' +
                "DPS: " + roundNumber(dps);
    }

    private String roundNumber(Number number) {
        return String.format("%.0f", number.floatValue());
    }

    @Override
    public void postTransitionIn(Transition transitionIn) {
        super.postTransitionIn(transitionIn);
        HulletHellGame.getInputManager().managedProcessors.add(menu);
    }

    @Override
    public void preTransitionOut(Transition transitionOut) {
        super.preTransitionOut(transitionOut);
        HulletHellGame.getInputManager().managedProcessors.remove(menu);
    }

    @Override
    public int getPreviousScreenId() {
        return MAIN_MENU_SCREEN;
    }

    @Override
    public int getId() {
        return GARAGE_SCREEN;
    }
}
