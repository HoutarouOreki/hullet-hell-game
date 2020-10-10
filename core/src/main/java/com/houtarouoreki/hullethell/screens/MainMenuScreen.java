package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.assets.AssetManager;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
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
    private UiContainer uiContainer;
    private Div buttonsContainer;
    private TextButton playButton;

    public MainMenuScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void initialise(GameContainer gc) {
        uiContainer = new UiContainer(gc, assetManager);

        buttonsContainer = new Container(100, 100, 200, gc.getHeight() - 200);
        buttonsContainer.setVisibility(Visibility.VISIBLE);
        uiContainer.add(buttonsContainer);

        playButton = createTextButton("Play", 0);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void onActionBegin(ActionEvent event) {
                playButton.setText("Clicked");
                System.out.println("Registered click");
            }

            @Override
            public void onActionEnd(ActionEvent event) {
                playButton.setText("Play");
            }
        });
        buttonsContainer.add(playButton);
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

    private TextButton createTextButton(String label, int row) {
        TextButton button = new TextButton();
        button.setVisibility(Visibility.VISIBLE);
        button.setWidthToContentWidthOf(buttonsContainer);
        button.setHeight(30);
        button.setText(label);
        button.getLabel().setY(-3);
        button.setY(row * 60);
        button.setEnabled(true);
        return button;
    }
}
