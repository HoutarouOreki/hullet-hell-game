package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.graphics.Color;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.PrimitiveBody;
import com.houtarouoreki.hullethell.collisions.Circle;
import com.houtarouoreki.hullethell.collisions.CollisionBodyManager;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.environment.Updatable;
import com.houtarouoreki.hullethell.graphics.Renderable;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedSection;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Body extends PrimitiveBody implements Renderable, Updatable {
    public Vector2 acceleration = new Vector2();
    public String name;
    private CollisionBodyManager collisionBodyManager;
    private boolean removed;
    private ScriptedSection section;
    private boolean shouldDespawnOOBounds;

    public Body() {
        collisionBodyManager = generateCollisionBodyManager();
        getCollisionBodyManager().addCollisionListener(this::onCollision);
    }

    public ScriptedSection getSection() {
        return section;
    }

    public void setSection(ScriptedSection section) {
        this.section = section;
        section.registerBody(this);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setVelocity(getVelocity().add(acceleration.scl(delta)));
        bodySpriteManager.update(delta);
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if (HulletHellGame.getSettings().debugging.getValue())
            renderCollisionBody(g);
    }

    private void renderCollisionBody(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Circle circle : getCollisionBodyManager().getCollisionBodyCopy(true)) {
            RenderHelpers.drawWorldCircle(circle.position, circle.radius, g);
        }
    }

    public void scale(float scale) {
        setSize(getSize().scl(scale));
        List<Circle> newCollisionBody = new ArrayList<>();
        for (Circle circle : getCollisionBodyManager().getCollisionBodyCopy())
            newCollisionBody.add(circle.scl(scale));
        getCollisionBodyManager().setCollisionBody(newCollisionBody);
    }

    protected void onCollision(CollisionResult collision) {
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved() {
        removed = true;
        if (section != null) {
            section.unregisterBody(this);
        }
    }

    public void setShouldDespawnOOBounds(boolean value) {
        shouldDespawnOOBounds = value;
    }

    public boolean shouldDespawnOOBounds() {
        return shouldDespawnOOBounds;
    }

    protected CollisionBodyManager generateCollisionBodyManager() {
        return new CollisionBodyManager(this);
    }

    public CollisionBodyManager getCollisionBodyManager() {
        if (collisionBodyManager == null)
            collisionBodyManager = generateCollisionBodyManager();
        return collisionBodyManager;
    }
}
