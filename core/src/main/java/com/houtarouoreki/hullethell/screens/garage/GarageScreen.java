package com.houtarouoreki.hullethell.screens.garage;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Container;
import com.houtarouoreki.hullethell.graphics.PaddingMargin;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.screens.HulletHellScreen;
import org.mini2Dx.core.screen.Transition;

import java.util.EnumSet;

public class GarageScreen extends HulletHellScreen {
    private final ShipsMenu menu;

    public GarageScreen() {
        Container menuContainer = new Container();
        container.add(menuContainer);
        menuContainer.add(menu = new ShipsMenu());
        menuContainer.setPadding(new PaddingMargin(5));
        menu.setAnchor(new Vector2(0.5f));
        menu.setOrigin(new Vector2(0.5f));
        menu.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
        menuContainer.setRelativeSizeAxes(EnumSet.of(Axes.VERTICAL));
        menuContainer.setSize(new Vector2(300, 1));
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
