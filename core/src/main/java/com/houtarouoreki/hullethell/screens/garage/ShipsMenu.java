package com.houtarouoreki.hullethell.screens.garage;

import com.badlogic.gdx.utils.Array;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ShipConfiguration;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.ui.Menu;

public class ShipsMenu extends Menu {
    public ShipsMenu() {
        addOptions();
        interconnectComponentsVertically(true);
        setSize(new Vector2(300, 500));
    }

    private void addOptions() {
        Array<ShipConfiguration> shipConfigurations = new Array<>();
        HulletHellGame.getAssetManager().getAll(ShipConfiguration.class, shipConfigurations);

        int i = 0;
        for (ShipConfiguration shipConfiguration : shipConfigurations) {
            ShipsMenuOption menuOption = new ShipsMenuOption(shipConfiguration);
            float spacing = 3;
            menuOption.setY(i * (menuOption.getSize().y + spacing));
            add(menuOption);
            i++;
        }
    }
}
