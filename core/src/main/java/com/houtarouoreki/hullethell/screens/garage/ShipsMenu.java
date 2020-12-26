package com.houtarouoreki.hullethell.screens.garage;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ShipConfiguration;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.ui.Menu;
import com.houtarouoreki.hullethell.ui.MenuComponent;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class ShipsMenu extends Menu {
    public ShipsMenu() {
        setAutoSizeAxes(EnumSet.of(Axes.VERTICAL));
        addOptions();
        interconnectComponentsVertically(false);
    }

    private void addOptions() {
        int i = 0;
        List<ShipConfiguration> shipConfigurations = new ArrayList<>();
        for (String shipConfigurationName : HulletHellGame.getPlayerState().unlockedShips) {
            ShipConfiguration c = HulletHellGame.getAssetManager()
                    .get("ships/" + shipConfigurationName + ".hhc");
            shipConfigurations.add(c);
        }
        for (ShipConfiguration shipConfiguration : shipConfigurations) {
            ShipsMenuOption menuOption = new ShipsMenuOption(shipConfiguration, shipConfigurations);
            float spacing = 3;
            menuOption.setY(i * (menuOption.getSize().y + spacing));
            add(menuOption);
            if (shipConfiguration.fileName.equals(HulletHellGame.getPlayerState().currentShipFileName.getValue()))
                menuOption.focus();
            menuOption.addListener(new MenuComponent.MenuComponentListener() {
                @Override
                public void onFocused() {
                    onShipSelected(shipConfiguration);
                }

                @Override
                public void onFocusLost() {
                }
            });
            i++;
        }
    }

    private void onShipSelected(ShipConfiguration shipConfiguration) {
        HulletHellGame.getPlayerState().currentShipFileName.setValue(shipConfiguration.fileName);
    }
}
