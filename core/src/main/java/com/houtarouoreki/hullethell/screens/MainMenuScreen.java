package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.SongConfiguration;
import com.houtarouoreki.hullethell.ui.Button;
import com.houtarouoreki.hullethell.ui.Menu;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

import java.util.Arrays;

public class MainMenuScreen extends HulletHellScreen {
    private final Menu menu;
    private int buttonY = -30;

    public MainMenuScreen() {
        menu = new Menu();
        int buttonX = 30;
        Vector2 buttonSize = new Vector2(200, 30);

        Button playButton = new Button();
        playButton.setPosition(new Vector2(buttonX, getNextButtonY()));
        playButton.setText("Play");
        playButton.setSize(buttonSize);
        playButton.focus();
        playButton.listener = new Button.ButtonListener() {
            @Override
            public void onAction() {
                onPlayButton();
            }
        };

        Button settingsButton = new Button();
        settingsButton.setPosition(new Vector2(buttonX, getNextButtonY()));
        settingsButton.setText("Settings");
        settingsButton.setSize(buttonSize);
        settingsButton.listener = new Button.ButtonListener() {
            @Override
            public void onAction() {
                onSettingsButton();
            }
        };

        Button exitButton = new Button();
        exitButton.setPosition(new Vector2(buttonX, getNextButtonY()));
        exitButton.setSize(buttonSize);
        exitButton.setText("Exit");
        exitButton.listener = new Button.ButtonListener() {
            @Override
            public void onAction() {
                System.exit(0);
            }
        };
        menu.addAll(Arrays.asList(playButton, settingsButton, exitButton));

        playButton.upperNeighbor = exitButton;
        playButton.lowerNeighbor = settingsButton;
        settingsButton.upperNeighbor = playButton;
        settingsButton.lowerNeighbor = exitButton;
        exitButton.upperNeighbor = settingsButton;
        exitButton.lowerNeighbor = playButton;
    }

    @Override
    public void initialise(GameContainer gc) {
    }

    @Override
    public void postTransitionIn(Transition transitionIn) {
        super.postTransitionIn(transitionIn);
        HulletHellGame.getInputManager().managedProcessors.add(menu);
        SongConfiguration currSong = HulletHellGame.getMusicManager()
                .getCurrentSongInfo();
        String menuSongName = "One Man Symphony - Beat 02";
        if (currSong == null || !currSong.toString().equals(menuSongName))
            HulletHellGame.getMusicManager().setCurrentSong(menuSongName);
        HulletHellGame.getMusicManager().setLooping(true);
        HulletHellGame.getMusicManager().play();
    }

    @Override
    public void preTransitionOut(Transition transitionOut) {
        super.preTransitionOut(transitionOut);
        HulletHellGame.getInputManager().managedProcessors.remove(menu);
    }

    private void onPlayButton() {
        HulletHellGame.getScreensManager().enterGameScreen(
                HulletHellScreen.LEVEL_SELECT_SCREEN,
                new FadeOutTransition(), new FadeInTransition());
        HulletHellGame.getMusicManager().fadeOut(0.5f);
    }

    private void onSettingsButton() {
        HulletHellGame.getScreensManager().enterGameScreen(
                4, new FadeOutTransition(), new FadeInTransition());
    }

    @Override
    public void update(GameContainer gc,
                       ScreenManager<? extends GameScreen> screenManager,
                       float delta) {
        menu.update(delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        menu.render(g);
    }

    @Override
    public int getId() {
        return MAIN_MENU_SCREEN;
    }

    private int getNextButtonY() {
        return buttonY += 60;
    }
}
