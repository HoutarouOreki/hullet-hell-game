package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Container;
import com.houtarouoreki.hullethell.graphics.PaddingMargin;
import com.houtarouoreki.hullethell.numbers.LoopInt;
import com.houtarouoreki.hullethell.ui.Button;
import com.houtarouoreki.hullethell.ui.Label;
import com.houtarouoreki.hullethell.ui.Menu;
import com.houtarouoreki.hullethell.ui.MenuComponent;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class LevelSelectScreen extends HulletHellScreen {
    private final Menu menu;
    private final Label levelTitle;
    private final Label songTitle;
    private final Label songAuthor;

    public LevelSelectScreen() {
        Container leftHalf = new Container();
        leftHalf.setPadding(new PaddingMargin(50));
        leftHalf.setSize(new Vector2(0.5f, 1));
        leftHalf.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        container.add(leftHalf);

        levelTitle = new Label();
        leftHalf.add(levelTitle);

        songTitle = new Label();
        songTitle.setPosition(new Vector2(0, 50));
        leftHalf.add(songTitle);

        songAuthor = new Label();
        songAuthor.setPosition(new Vector2(0, 100));
        leftHalf.add(songAuthor);

        Container rightHalf = new Container();
        rightHalf.setPosition(new Vector2(0.5f, 0));
        rightHalf.setRelativePositionAxes(EnumSet.of(Axes.HORIZONTAL));
        rightHalf.setSize(new Vector2(0.5f, 1));
        rightHalf.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        rightHalf.setPadding(new PaddingMargin(50));
        container.add(rightHalf);

        List<Button> stageButtons = new ArrayList<Button>();
        Array<StageConfiguration> h = new Array<StageConfiguration>();
        HulletHellGame.getAssetManager().getAll(StageConfiguration.class, h);
        System.out.println("HHHHHHHHH");
        for (final StageConfiguration stage : h) {
            System.out.println("stage.name = " + stage.name);
            Button button = new Button();
            button.setText(stage.name);
            button.addListener(createButtonSelectListener(stage));
            button.listener = new Button.ButtonListener() {
                @Override
                public void onAction() {
                    onStageSelected(stage);
                }
            };

            stageButtons.add(button);
        }
        stageButtons.get(0).focus();

        LoopInt i = new LoopInt(stageButtons);
        do {
            Button button = stageButtons.get(i.getValue());
            button.upperNeighbor = stageButtons.get(i.decremented());
            button.lowerNeighbor = stageButtons.get(i.incremented());
            button.setSize(new Vector2(1, 40));
            button.setPosition(new Vector2(0, i.getValue() * (button.getSize().y + 10)));
            button.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
            i.increment();
        } while (i.getValue() != 0);

        menu = new Menu();
        menu.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        menu.addAll(stageButtons);
        rightHalf.add(menu);
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
    public int getId() {
        return LEVEL_SELECT_SCREEN;
    }

    private void onStageSelected(StageConfiguration stageConf) {
        PlayScreen playScreen = (PlayScreen)
                HulletHellGame.getScreensManager().getGameScreen(PLAY_SCREEN);
        playScreen.setStage(stageConf);
        HulletHellGame.getScreensManager().enterGameScreen(PLAY_SCREEN,
                new FadeOutTransition(), new FadeInTransition());
    }

    private MenuComponent.MenuComponentListener createButtonSelectListener(final StageConfiguration s) {
        return new MenuComponent.MenuComponentListener() {
            @Override
            public void onFocused() {
                levelTitle.setText(s.name);
            }

            @Override
            public void onFocusLost() {
            }
        };
    }
}
