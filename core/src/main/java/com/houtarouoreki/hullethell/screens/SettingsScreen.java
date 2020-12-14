package com.houtarouoreki.hullethell.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.configurations.SerializableSettings;
import com.houtarouoreki.hullethell.configurations.Settings;
import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Rectangle;
import com.houtarouoreki.hullethell.input.ControlProcessor;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.numbers.LoopInt;
import com.houtarouoreki.hullethell.ui.*;
import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.playerdata.PlayerDataException;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class SettingsScreen extends HulletHellScreen implements ControlProcessor {
    private final Menu menu;

    public SettingsScreen() {
        List<SettingsComponent> settingsComponents = new ArrayList<SettingsComponent>();

        Settings settings = HulletHellGame.getSettings();

        container.add(menu = new Menu());
        menu.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
        menu.setPadding(new Vector2(60, 60));

        SliderFloat.SliderTextGenerator
                volumeTextGenerator = new SliderFloat.SliderTextGenerator() {
            @Override
            public String generateText(float value) {
                return ((Integer) Math.round(value * 100)).toString();
            }
        };

        SliderFloat musicVolume = new SliderFloat(.7f, 0,
                1);
        musicVolume.value.bindTo(settings.musicVolume);
        settingsComponents.add(new SettingsComponent("Music volume",
                musicVolume));
        musicVolume.textGenerator = volumeTextGenerator;

        SliderFloat sfxVolume = new SliderFloat(0.7f, 0,
                1);
        sfxVolume.value.bindTo(settings.sfxVolume);
        settingsComponents.add(new SettingsComponent("SFX volume",
                sfxVolume));
        sfxVolume.textGenerator = volumeTextGenerator;

        settingsComponents.add(generateSwitch("Fullscreen",
                settings.fullScreen));

        settingsComponents.add(generateSwitch("Backgrounds",
                settings.backgrounds));

        settingsComponents.add(generateSwitch("Debug rendering",
                settings.debugging));

        settingsComponents.add(generateSwitch("Show FPS",
                settings.renderFPS));

        generateLayout(settingsComponents);
    }

    private SettingsComponent generateSwitch(String label,
                                             Bindable<Boolean> setting) {
        Switch toggle = new Switch();
        toggle.value.bindTo(setting);
        return new SettingsComponent(label, toggle);
    }

    @Override
    public void initialise(GameContainer gc) {
    }

    @Override
    public void postTransitionIn(Transition transitionIn) {
        super.postTransitionIn(transitionIn);
        HulletHellGame.getInputManager().managedProcessors.add(menu);
        HulletHellGame.getInputManager().managedProcessors.add(this);
    }

    @Override
    public void preTransitionOut(Transition transitionOut) {
        super.preTransitionOut(transitionOut);
        HulletHellGame.getInputManager().managedProcessors.remove(menu);
        HulletHellGame.getInputManager().managedProcessors.remove(this);
        try {
            Mdx.playerData.writeJson(
                    new SerializableSettings(HulletHellGame.getSettings()),
                    "playerData.json");
        } catch (PlayerDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getId() {
        return SETTINGS_SCREEN;
    }

    @Override
    public boolean handleControl(Controls control) {
        if (control == Controls.back) {
            HulletHellGame.getScreensManager().enterGameScreen(MAIN_MENU_SCREEN,
                    new FadeOutTransition(), new FadeInTransition());
            return true;
        }
        return false;
    }

    private void generateLayout(List<SettingsComponent> components) {
        float spacing = 2;
        float currentY = 0;

        for (LoopInt i = new LoopInt(0, components.size() - 1); ; i.increment()) {
            SettingsComponent component = components.get(i.getValue());
            component.setPosition(new Vector2(0, currentY));
            component.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL));
            component.setSize(new Vector2(1, 50));
            component.upperNeighbor = components.get(i.decremented());
            component.lowerNeighbor = components.get(i.incremented());
            menu.add(component);
            currentY += component.getSize().y + spacing;
            if (i.getValue().equals(i.getMax()))
                break;
        }
        components.get(0).focus();
    }

    private static class SettingsComponent extends MenuComponent {
        private final Label label;
        private final MenuComponent settingControl;
        private final Rectangle background;
        private boolean layoutValid;

        public SettingsComponent(String labelText, MenuComponent settingControl) {
            background = new Rectangle();
            background.setRelativeSizeAxes(EnumSet.allOf(Axes.class));
            background.setColor(Color.valueOf("111166"));
            background.setVisibility(false);
            background.setMargin(new Vector2(-8, -8));
            add(background);

            label = new Label();
            label.setText(labelText);
            label.alignment = Align.center;
            add(label);

            this.settingControl = settingControl;
            add(settingControl);

            generateLayout();
        }

        @Override
        public void onUpdate(float delta) {
            if (!layoutValid)
                generateLayout();
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
            background.setVisibility(true);
        }

        @Override
        protected void onFocusLost() {
            super.onFocusLost();
            settingControl.unfocus();
            background.setVisibility(false);
        }

        private void invalidateLayout() {
            layoutValid = false;
        }

        private void generateLayout() {
            setPadding(new Vector2(8, 8));

            label.setRelativePositionAxes(EnumSet.of(Axes.HORIZONTAL, Axes.VERTICAL));
            label.setPosition(new Vector2(0, 0));
            label.setRelativeSizeAxes(EnumSet.of(Axes.HORIZONTAL, Axes.VERTICAL));
            label.setSize(new Vector2(0.5f, 1));

            settingControl.setRelativePositionAxes(EnumSet
                    .of(Axes.HORIZONTAL, Axes.VERTICAL));
            settingControl.setPosition(new Vector2(0.5f, 0));
            settingControl.setRelativeSizeAxes(EnumSet
                    .of(Axes.HORIZONTAL, Axes.VERTICAL));
            settingControl.setSize(new Vector2(0.5f, 1));
            layoutValid = true;
        }
    }
}
