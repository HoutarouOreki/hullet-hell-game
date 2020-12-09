package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.ui.Button;
import com.houtarouoreki.hullethell.ui.Menu;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;
import org.mini2Dx.ui.UiContainer;
import org.mini2Dx.ui.style.UiTheme;

import java.util.Arrays;

public class MainMenuScreen extends BasicGameScreen {
    private final HulletHellGame game;
    private Menu menu;

    public MainMenuScreen(HulletHellGame game) {
        this.game = game;
    }

    @Override
    public void initialise(GameContainer gc) {
        menu = new Menu();
        Button t1 = new Button();
        t1.position = new Vector2(30, 30);
        t1.label = "Play";
        t1.size = new Vector2(200, 30);
        t1.focus();
        t1.listener = new Button.ButtonListener() {
            @Override
            public void onAction() {
                onPlayButton();
            }
        };
        Button t2 = new Button();
        t2.position = new Vector2(30, 90);
        t2.size = new Vector2(200, 30);
        t2.label = "Exit";
        t2.listener = new Button.ButtonListener() {
            @Override
            public void onAction() {
                System.exit(0);
            }
        };
        menu.components.addAll(Arrays.asList(t1, t2));

        t1.lowerNeighbor = t2;
        t2.upperNeighbor = t1;
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
}
