package com.houtarouoreki.hullethell.screens;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.entities.Entity;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.environment.BackgroundObject;
import com.houtarouoreki.hullethell.environment.BackgroundStar;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.input.Controls;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedStageUpdateException;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.Transition;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends HulletHellScreen {
    public static Viewport viewport;
    private final int starsAmount = 200;
    private final List<BackgroundObject> stars = new ArrayList<>(starsAmount);
    private final DialogueBox dialogueBox;
    private StageConfiguration script;
    private World world;

    public PlayScreen() {
        viewport = new FitViewport(1280,
                1280 * World.viewArea.y / World.viewArea.x);
        container.add(dialogueBox = new DialogueBox());
    }

    public void setStage(StageConfiguration script) {
        this.script = script;
    }

    @Override
    public void preTransitionIn(Transition transitionIn) {
        super.preTransitionIn(transitionIn);
        if (world != null)
            container.remove(world.questManager);
        world = new World(script, dialogueBox);
        Ship player = world.player;
        player.setCollisionCooldown(2);
        player.getCollisionBodyManager().setTeam(CollisionTeam.PLAYER_SHIP);
        player.setPosition(new Vector2(World.viewArea.x * 0.1f, World.viewArea.y * 0.5f));
        player.onItemCollected =
                item -> world.statistics.addItem(item.name);
        world.addBody(player);
        container.add(world.questManager);
        dialogueBox.reset();

        initialiseBackground();
    }

    @Override
    public void preTransitionOut(Transition transitionOut) {
        super.preTransitionOut(transitionOut);
        world.stop();
        HulletHellGame.getMusicManager().fadeOut(1);
    }

    @Override
    public void initialise(GameContainer gc) {
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager,
                       float delta) {
        super.update(gc, screenManager, delta);
        updateSteering();

        updateBackground(delta);
        try {
            world.update(delta);
        } catch (ScriptedStageUpdateException e) {
            screenManager.enterGameScreen(SCRIPT_ERROR_SCREEN, new FadeOutTransition(), new FadeInTransition());
            ((ScriptErrorScreen) screenManager.getGameScreen(SCRIPT_ERROR_SCREEN)).setException(e);
        }
        if (world.isFinished()) {
            screenManager.enterGameScreen(3, new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public int getPreviousScreenId() {
        return LEVEL_SELECT_SCREEN;
    }

    private void updateSteering() {
        Ship player = world.player;
        float targetX = 0;
        float targetY = 0;
        if (HulletHellGame.getInputManager().isControlActive(Controls.left))
            targetX -= 1;
        if (HulletHellGame.getInputManager().isControlActive(Controls.right))
            targetX += 1;
        if (HulletHellGame.getInputManager().isControlActive(Controls.down))
            targetY += 1;
        if (HulletHellGame.getInputManager().isControlActive(Controls.up))
            targetY -= 1;
        if (HulletHellGame.getInputManager().isControlActive(Controls.shoot)) {
            Entity ammunition = player.shoot();
            if (ammunition != null) {
                world.addBody(ammunition);
                ammunition.getCollisionBodyManager().setTeam(CollisionTeam.PLAYER_BULLETS);
                HulletHellGame.getSoundManager()
                        .playSound("laser1", 0.3f);
            }
        }

        Vector2 targetDirection = new Vector2(targetX, targetY);
        if (targetDirection.len2() > 0)
            player.move(targetDirection.angle());
        else
            player.stop();
    }

    private void initialiseBackground() {
        final float minStarSize = 0.04f;
        final float maxStarSize = 0.07f;
        for (int i = 0; i < starsAmount; i++) {
            float starSize = minStarSize + (float) Math.random() * (maxStarSize - minStarSize);
            BackgroundStar star = new BackgroundStar((float) Math.random() * World.viewArea.x,
                    (float) Math.random() * World.viewArea.y, starSize);
            star.setVelocity(new Vector2((float) Math.random() * -0.2f - 0.2f, 0));
            stars.add(star);
        }
    }

    private void updateBackground(float delta) {
        for (BackgroundObject star : stars) {
            star.update(delta);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        viewport.apply(g);
        if (HulletHellGame.getSettings().backgrounds.getValue())
            drawBackground(g);
        world.render(g);
        viewport.unapply(g);
        super.render(gc, g);
    }

    private void drawBackground(Graphics g) {
        for (BackgroundObject star : stars) {
            star.render(g);
        }
    }

    @Override
    public int getId() {
        return PLAY_SCREEN;
    }
}
