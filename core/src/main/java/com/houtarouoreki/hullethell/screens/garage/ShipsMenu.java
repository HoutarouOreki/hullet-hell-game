package com.houtarouoreki.hullethell.screens.garage;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ShipConfiguration;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.ui.Menu;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ShipsMenu extends Menu {
    public ShipsMenu() {
        setAutoSizeAxes(EnumSet.of(Axes.VERTICAL));
        addOptions();
        interconnectComponentsVertically(true);
    }

    private void addOptions() {

        int i = 0;
        List<ShipConfiguration> shipConfigurations = new ArrayList<>();
        float minScaleY = 500000;
        float minScaleX = 500000;
        for (String shipConfigurationName : HulletHellGame.getPlayerState().unlockedShips) {
            ShipConfiguration c = HulletHellGame.getAssetManager()
                    .get("ships/" + shipConfigurationName + ".cfg");
            shipConfigurations.add(c);
            minScaleY = Math.min(minScaleY, ShipsMenuOption.getYScaleToFitThumbnail(c.size));
            minScaleX = Math.min(minScaleX, ShipsMenuOption.getXScaleToFitThumbnail(c.size));
        }
        for (ShipConfiguration shipConfiguration : shipConfigurations) {
            ShipsMenuOption menuOption = new ShipsMenuOption(shipConfiguration, Math.min(minScaleX, minScaleY));
            float spacing = 3;
            menuOption.setY(i * (menuOption.getSize().y + spacing));
            add(menuOption);
            i++;
        }
    }
}
