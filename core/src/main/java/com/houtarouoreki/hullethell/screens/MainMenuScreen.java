package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.ui.Button;
import com.houtarouoreki.hullethell.ui.Menu;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;
import org.mini2Dx.ui.UiContainer;
import org.mini2Dx.ui.style.UiTheme;

import java.util.Arrays;

public class MainMenuScreen extends HulletHellScreen {
    private Menu menu;
    private int buttonY = -30;

    public MainMenuScreen(HulletHellGame game) {
        super(game);
    }

    @Override
    public void initialise(GameContainer gc) {
        menu = new Menu();
        int buttonX = 30;
        Vector2 buttonSize = new Vector2(200, 30);

        Button playButton = new Button();
        playButton.position = new Vector2(buttonX, getNextButtonY());
        playButton.label = "Play";
        playButton.size = buttonSize;
        playButton.focus();
        playButton.listener = new Button.ButtonListener() {
            @Override
            public void onAction() {
                onPlayButton();
            }
        };

        Button settingsButton = new Button();
        settingsButton.position = new Vector2(buttonX, getNextButtonY());
        settingsButton.label = "Settings";
        settingsButton.size = buttonSize;
        settingsButton.listener = new Button.ButtonListener() {
            @Override
            public void onAction() {
                onSettingsButton();
            }
        };

        Button exitButton = new Button();
        exitButton.position = new Vector2(buttonX, getNextButtonY());
        exitButton.size = buttonSize;
        exitButton.label = "Exit";
        exitButton.listener = new Button.ButtonListener() {
            @Override
            public void onAction() {
                System.exit(0);
            }
        };
        menu.components.addAll(Arrays.asList(playButton, settingsButton, exitButton));

        playButton.upperNeighbor = exitButton;
        playButton.lowerNeighbor = settingsButton;
        settingsButton.upperNeighbor = playButton;
        settingsButton.lowerNeighbor = exitButton;
        exitButton.upperNeighbor = settingsButton;
        exitButton.lowerNeighbor = playButton;
    }

    @Override
    public void postTransitionIn(Transition transitionIn) {
        super.postTransitionIn(transitionIn);
        game.getInputManager().managedProcessors.add(menu);
    }

    @Override
    public void preTransitionOut(Transition transitionOut) {
        super.preTransitionOut(transitionOut);
        game.getInputManager().managedProcessors.remove(menu);
    }

    private void onPlayButton() {
        game.getScreenManager().enterGameScreen(
                2, new FadeOutTransition(), new FadeInTransition());
    }

    private void onSettingsButton() {
        game.getScreenManager().enterGameScreen(
                4, new FadeOutTransition(), new FadeInTransition());
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager,
                       float delta) {
        if (!UiContainer.isThemeApplied())
            UiContainer.setTheme(game.getAssetManager().get(UiTheme.DEFAULT_THEME_FILENAME,
                    UiTheme.class));
        menu.update();
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {
        //menu.interpolate(alpha);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        menu.render(g);
    }

    @Override
    public int getId() {
        return 1;
    }

    private int getNextButtonY() {
        return buttonY += 60;
    }
}
