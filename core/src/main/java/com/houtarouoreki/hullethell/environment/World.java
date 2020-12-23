package com.houtarouoreki.hullethell.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.audio.CollisionSoundManager;
import com.houtarouoreki.hullethell.collisions.CollisionManager;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.entities.Entity;
import com.houtarouoreki.hullethell.entities.Item;
import com.houtarouoreki.hullethell.entities.Ship;
import com.houtarouoreki.hullethell.graphics.WorldRenderingManager;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.ScriptedStageManager;
import com.houtarouoreki.hullethell.scripts.quests.QuestManager;
import com.houtarouoreki.hullethell.scripts.quests.Statistics;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {
    public final static Vector2 viewArea = new Vector2(36, 20);
    public final static float time_step_duration = 0.01f;
    public final QuestManager questManager;
    public final Statistics statistics;
    public final ScriptedStageManager scriptedStageManager;
    public final Ship player;
    private final List<Body> bodies;
    private final List<Body> bodiesToAdd;
    private final List<Body> bodiesToRemove;
    private final CollisionManager collisionManager;
    private final CollisionSoundManager collisionSoundManager;
    private final WorldRenderingManager renderingManager;
    private int ticksPassed;
    private float bufferedTime;

    public World(StageConfiguration script, DialogueBox dialogueBox) {
        player = new Ship("Ship 1");
        statistics = new Statistics();
        questManager = new QuestManager();
        bodies = new ArrayList<>();
        bodiesToAdd = new ArrayList<>();
        bodiesToRemove = new ArrayList<>();
        collisionManager = new CollisionManager(this);
        collisionSoundManager = new CollisionSoundManager(collisionManager);
        scriptedStageManager = new ScriptedStageManager(this, script, dialogueBox);
        renderingManager = new WorldRenderingManager();
    }

    public List<Body> getBodies() {
        return Collections.unmodifiableList(bodies);
    }

    public void render(Graphics g) {
        renderingManager.render(g);
        if (HulletHellGame.getSettings().debugging.getValue())
            renderDebugInfo(g);
        renderProgressBar(g);
    }

    public void addBody(Body body) {
        bodiesToAdd.add(body);
    }

    public void removeBody(Body body) {
        bodiesToRemove.add(body);
    }

    private void registerBody(Body body) {
        renderingManager.registerBody(body);
    }

    private void unregisterBody(Body body) {
        body.setRemoved();
        renderingManager.unregisterBody(body);
    }

    public float getTimePassed() {
        return ticksPassed * time_step_duration;
    }

    public int getTicksPassed() {
        return ticksPassed;
    }

    private void renderDebugInfo(Graphics g) {
        g.drawString("Bodies: "
                + bodies.size(), 20, 20);
        g.drawString("Current section: "
                + scriptedStageManager.getCurrentStageName(), 20, 35);
        g.drawString("Active bodies: "
                + scriptedStageManager.getActiveBodiesCount(), 20, 50);
        g.drawString("Bodies: "
                + scriptedStageManager.getBodiesCount(), 20, 65);
        g.drawString("Waiting bodies: "
                + scriptedStageManager.getWaitingBodiesCount(), 20, 80);
        g.drawString("Waiting section actions: "
                + scriptedStageManager.getSectionWaitingActions(), 20, 95);
        g.drawString("Current section actions: "
                + scriptedStageManager.getSectionCurrentActions(), 20, 110);
    }

    private void renderProgressBar(Graphics g) {
        g.setColor(new Color(1, 1, 1, 0.8f));
        g.fillRect(0, 700, scriptedStageManager.getProgression() * 1280, 20);
    }

    public void update(float delta) {
        bufferedTime += delta;
        while (bufferedTime >= time_step_duration) {
            physics();
            clampPlayerPosition();
            addBodies();
            removeBodies();
            collisionSoundManager.update(delta);
            bufferedTime -= time_step_duration;
            ticksPassed++;
            scriptedStageManager.update(delta);
            renderingManager.update(delta);
        }
    }

    private void clampPlayerPosition() {
        player.setPosition(
                new Vector2(
                        MathUtils.clamp(player.getPosition().x, 0, World.viewArea.x),
                        MathUtils.clamp(player.getPosition().y, 0, World.viewArea.y)));
    }

    private void addBodies() {
        for (Body body : bodiesToAdd) {
            bodies.add(body);
            registerBody(body);
        }
        bodiesToAdd.clear();
    }

    private void removeBodies() {
        for (Body body : bodiesToRemove) {
            bodies.remove(body);
            unregisterBody(body);
        }
        bodiesToRemove.clear();
    }

    public boolean isFinished() {
        return scriptedStageManager.isFinished();
    }

    protected void physics() {
        for (Body body : bodies) {
            if (body.shouldDespawnOOBounds()) {
                float despawnMarginX = body.getSize().x;
                float despawnMarginY = body.getSize().y;
                if (body.getPosition().x < -despawnMarginX ||
                        body.getPosition().y < -despawnMarginY ||
                        body.getPosition().x > viewArea.x + despawnMarginX ||
                        body.getPosition().y > viewArea.y + despawnMarginY) {
                    removeBody(body);
                    continue;
                }
            }
            if (body instanceof Entity && !((Entity) body).isAlive()) { // check entity's health
                removeBody(body);
                for (Item item : ((Entity) body).items) {
                    item.setPosition(body.getPosition());
                    item.setVelocity(body.getVelocity().scl(0.5f));
                    addBody(item);
                }
                continue;
            }
            body.update(time_step_duration);
        }
        collisionManager.RunCollisions();
        renderingManager.addCollisions(collisionManager.currentStepCollisions);
    }

    public void stop() {
        collisionSoundManager.stop();
    }

    public Body getBody(String name) {
        for (Body body : bodies) {
            if (body.name != null && body.name.equals(name))
                return body;
        }
        return null;
    }
}
