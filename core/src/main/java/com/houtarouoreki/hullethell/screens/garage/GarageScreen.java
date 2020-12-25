package com.houtarouoreki.hullethell.screens.garage;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.screens.HulletHellScreen;
import org.mini2Dx.core.screen.Transition;

public class GarageScreen extends HulletHellScreen {
    private final ShipsMenu menu;

    public GarageScreen() {
        container.add(menu = new ShipsMenu());
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
