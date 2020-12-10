package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.numbers.LoopInt;
import com.houtarouoreki.hullethell.ui.*;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.List;

public class SettingsScreen extends HulletHellScreen implements ControlProcessor {
    private Menu menu;

    public SettingsScreen(HulletHellGame game) {
        super(game);
    }

    @Override
    public void initialise(GameContainer gc) {
        List<SettingsComponent> settingsComponents = new ArrayList<SettingsComponent>();
        menu = new Menu();

        Slider musicVolume = new Slider(70, 0,
                100, game.getInputManager());
        settingsComponents.add(new SettingsComponent("Music volume", musicVolume));

        Slider sfxVolume = new Slider(70, 0,
                100, game.getInputManager());
        settingsComponents.add(new SettingsComponent("SFX volume", sfxVolume));

        Switch backgrounds = new Switch(new Switch.SwitchListener() {
            @Override
            public void onValueChanged(boolean newValue) {}
        });
        backgrounds.setValue(true);
        settingsComponents.add(new SettingsComponent("Backgrounds", backgrounds));

        generateLayout(settingsComponents);
    }

    @Override
    public void postTransitionIn(Transition transitionIn) {
        super.postTransitionIn(transitionIn);
        game.getInputManager().managedProcessors.add(menu);
        game.getInputManager().managedProcessors.add(this);
    }

    @Override
    public void preTransitionOut(Transition transitionOut) {
        super.preTransitionOut(transitionOut);
        game.getInputManager().managedProcessors.remove(menu);
        game.getInputManager().managedProcessors.remove(this);
    }

    @Override
    public void update(GameContainer gc,
                       ScreenManager<? extends GameScreen> screenManager, float delta) {
        menu.update(delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        menu.render(g);
    }

    @Override
    public int getId() {
        return SETTINGS_SCREEN;
    }

    @Override
    public boolean handleControl(Controls control) {
        if (control == Controls.back) {
            game.getScreenManager().enterGameScreen(MAIN_MENU_SCREEN,
                    new FadeOutTransition(), new FadeInTransition());
            return true;
        }
        return false;
    }

    private void generateLayout(List<SettingsComponent> components) {
        float margins = 60;
        float spacing = 2;
        float currentY = margins;
        Vector2 componentDimensions = new Vector2(1280 - margins * 2, 50);

        for (LoopInt i = new LoopInt(0, components.size() - 1); ; i.increment()) {
            SettingsComponent component = components.get(i.getValue());
            component.setPosition(new Vector2(margins, currentY));
            component.setSize(componentDimensions);
            component.upperNeighbor = components.get(i.decremented());
            component.lowerNeighbor = components.get(i.incremented());
            menu.components.add(component);
            currentY += componentDimensions.y + spacing;
            if (i.getValue().equals(i.getMax()))
                break;
        }
        components.get(0).focus();
    }

    private static class SettingsComponent extends MenuComponent {
        private final Label label;
        private final MenuComponent settingControl;
        private boolean layoutValid;

        public SettingsComponent(String labelText, MenuComponent settingControl) {
            label = new Label();
            label.setText(labelText);
            label.alignment = Align.center;
            this.settingControl = settingControl;
        }

        @Override
        public void update(float delta) {
            super.update(delta);
            if (!layoutValid)
                generateLayout();
            settingControl.update(delta);
            label.update(delta);
        }

        @Override
        public void render(Graphics g) {
            super.render(g);
            g.setColor(Color.valueOf("111166"));
            if (isCurrentlyFocused()) {
                g.fillRect(getPosition().x, getPosition().y,
                        getSize().x, getSize().y);
            }
            label.render(g);
            settingControl.render(g);
        }

        @Override
        public boolean handleControl(Controls control) {
            return settingControl.handleControl(control);
        }

        @Override
        public void setPosition(Vector2 position) {
            super.setPosition(position);
            invalidateLayout();
        }

        @Override
        public void setSize(Vector2 size) {
            super.setSize(size);
            invalidateLayout();
        }

        @Override
        protected void onFocused() {
            super.onFocused();
            settingControl.focus();
        }

        @Override
        protected void onFocusLost() {
            super.onFocusLost();
            settingControl.unfocus();
        }

        private void invalidateLayout() {
            layoutValid = false;
        }

        private void generateLayout() {
            Vector2 padding = new Vector2(8, 8);

            label.setPosition(getPosition().add(padding));
            Vector2 halfSize = getSize().scl(new Vector2(0.5f, 1))
                    .sub(padding.cpy().scl(2));
            label.setSize(halfSize);
            settingControl.setPosition(getPosition()
                    .add(getSize().scl(new Vector2(0.5f, 0))).add(padding));
            settingControl.setSize(halfSize);
            layoutValid = true;
        }
    }
}
