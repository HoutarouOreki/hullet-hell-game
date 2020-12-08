package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;
import org.mini2Dx.ui.UiContainer;
import org.mini2Dx.ui.element.Container;
import org.mini2Dx.ui.element.Div;
import org.mini2Dx.ui.element.TextButton;
import org.mini2Dx.ui.element.Visibility;
import org.mini2Dx.ui.event.ActionEvent;
import org.mini2Dx.ui.listener.ActionListener;
import org.mini2Dx.ui.style.UiTheme;

public class MainMenuScreen extends BasicGameScreen {
    private final AssetManager assetManager;
    private final ScreenManager<? extends GameScreen> screenManager;
    private UiContainer uiContainer;
    private Div buttonsContainer;

    public MainMenuScreen(AssetManager assetManager, ScreenManager<? extends GameScreen> screenManager) {
        this.assetManager = assetManager;
        this.screenManager = screenManager;
    }

    @Override
    public void initialise(GameContainer gc) {
        uiContainer = new UiContainer(gc, assetManager);
        Gdx.input.setInputProcessor(uiContainer);

        buttonsContainer = new Container(100, 100, 200, gc.getHeight() - 200);
        buttonsContainer.setVisibility(Visibility.VISIBLE);
        uiContainer.add(buttonsContainer);

        createAndAddTextButton("Play", new ActionListener() {
            @Override
            public void onActionBegin(ActionEvent event) {
            }

            @Override
            public void onActionEnd(ActionEvent event) {
                onPlayButton();
            }
        });

        createAndAddTextButton("Exit", new ActionListener() {
            @Override
            public void onActionBegin(ActionEvent event) {
            }

            @Override
            public void onActionEnd(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    @Override
    public void postTransitionIn(Transition transitionIn) {
        super.postTransitionIn(transitionIn);
        Gdx.input.setInputProcessor(uiContainer);
    }

    @Override
    public void preTransitionOut(Transition transitionOut) {
        super.preTransitionOut(transitionOut);
        Gdx.input.setInputProcessor(null);
    }

    private void onPlayButton() {
        screenManager.enterGameScreen(2, new FadeOutTransition(), new FadeInTransition());
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager, float delta) {
        if (!UiContainer.isThemeApplied())
            UiContainer.setTheme(assetManager.get(UiTheme.DEFAULT_THEME_FILENAME, UiTheme.class));
        uiContainer.update(delta);
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {
        uiContainer.interpolate(alpha);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        uiContainer.render(g);
    }

    @Override
    public int getId() {
        return 1;
    }

    private void createAndAddTextButton(String label, ActionListener actionListener) {
        TextButton button = new TextButton();
        button.setVisibility(Visibility.VISIBLE);
        button.setWidthToContentWidthOf(buttonsContainer);
        button.setHeight(30);
        button.setText(label);
        button.getLabel().setY(-3);
        button.setY(buttonsContainer.getTotalChildren() * 60);
        button.setEnabled(true);
        button.addActionListener(actionListener);
        buttonsContainer.add(button);
    }
}
