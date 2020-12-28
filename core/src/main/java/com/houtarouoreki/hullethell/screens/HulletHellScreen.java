package com.houtarouoreki.hullethell.screens;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.SerializablePlayerState;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.ui.WindowSizeContainer;
import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.playerdata.PlayerDataException;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

public abstract class HulletHellScreen extends BasicGameScreen implements ControlProcessor {
    public static final int LOADING_SCREEN = 0;
    public static final int MAIN_MENU_SCREEN = 1;
    public static final int PLAY_SCREEN = 2;
    public static final int RESULTS_SCREEN = 3;
    public static final int SETTINGS_SCREEN = 4;
    public static final int LEVEL_SELECT_SCREEN = 5;
    public static final int GARAGE_SCREEN = 6;
    public static final int SCRIPT_ERROR_SCREEN = 7;
    public final WindowSizeContainer container = new WindowSizeContainer();

    @Override
    public void postTransitionIn(Transition transitionIn) {
        super.postTransitionIn(transitionIn);
        HulletHellGame.getInputManager().managedProcessors.add(this);
    }

    @Override
    public void preTransitionOut(Transition transitionOut) {
        super.preTransitionOut(transitionOut);
        savePlayerState();
        HulletHellGame.getInputManager().managedProcessors.remove(this);
    }

    private void savePlayerState() {
        try {
            Mdx.playerData.writeJson(
                    new SerializablePlayerState(HulletHellGame.getPlayerState()),
                    "playerState.json");
        } catch (PlayerDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialise(GameContainer gc) {
    }

    @Override
    public final void interpolate(GameContainer gc, float alpha) {
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        container.render(g);
    }

    @Override
    public void update(GameContainer gc,
                       ScreenManager<? extends GameScreen> screenManager, float delta) {
        container.update(delta);
    }

    public abstract int getPreviousScreenId();

    @Override
    public boolean handleControl(Controls control) {
        if (control == Controls.back) {
            HulletHellGame.getScreensManager().enterGameScreen(getPreviousScreenId(),
                    new FadeOutTransition(), new FadeInTransition());
            return true;
        }
        return false;
    }
}
