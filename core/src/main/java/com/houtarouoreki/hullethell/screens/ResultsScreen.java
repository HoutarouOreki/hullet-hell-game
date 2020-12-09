package com.houtarouoreki.hullethell.screens;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.input.Controls;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

public class ResultsScreen extends HulletHellScreen {

    public ResultsScreen(HulletHellGame game) {
        super(game);
    }

    @Override
    public void initialise(GameContainer gc) { }

    @Override
    public void update(GameContainer gameContainer, ScreenManager<? extends GameScreen> screenManager, float v) {
        if (game.getInputManager().isControlActive(Controls.select) ||
                game.getInputManager().isControlActive(Controls.back))
            screenManager.enterGameScreen(1, new FadeOutTransition(), new FadeInTransition());
    }

    @Override
    public void interpolate(GameContainer gameContainer, float v) {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.drawString("Press ENTER to continue", gameContainer.getWidth() * 0.2f,
                gameContainer.getHeight() * 0.8f);
    }

    @Override
    public int getId() {
        return 3;
    }
}
