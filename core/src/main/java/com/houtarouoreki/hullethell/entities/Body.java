package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.graphics.Color;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.PrimitiveBody;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.environment.Updatable;
import com.houtarouoreki.hullethell.graphics.Renderable;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.scripts.ScriptedSection;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Body extends PrimitiveBody implements Renderable, Updatable {
    public final List<Body> dontCollideWith = new ArrayList<>();
    private final List<CollisionCircle> collisionBody;
    public Vector2 acceleration = new Vector2();
    public String name;
    public EnumSet<CollisionTeam> collidesWith = EnumSet.noneOf(CollisionTeam.class);
    private int lastCollisionTick = -1;
    private CollisionTeam team;
    private boolean acceptsCollisions = true;
    private boolean removed;
    private ScriptedSection section;
    private boolean shouldDespawnOOBounds;
    private float collisionsDisabledFor;
    private double collisionsDisabledOn;

    public Body(List<CollisionCircle> collisionBody) {
        this.collisionBody = collisionBody;
    }

    public Body() {
        collisionBody = new ArrayList<>();
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
    }

    public float getFarthestPointDistance() {
        float farthestDistance = 0;
        for (CollisionCircle circle : getCollisionBody()) {
            float distance = circle.getDistanceFromCenter(0, 0) + circle.getRadius();
            if (distance > farthestDistance)
                farthestDistance = distance;
        }
        return farthestDistance;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if (HulletHellGame.getSettings().debugging.getValue())
            renderCollisionBody(g);
    }

    private void renderCollisionBody(Graphics g) {
        g.setColor(Color.YELLOW);
        for (CollisionCircle circle : getCollisionBody()) {
            RenderHelpers.drawWorldCircle(
                    getPosition().add(new Vector2(circle.getX(), circle.getY())),
                    circle.getRadius(), g);
        }
    }

    public List<CollisionCircle> getCollisionBody() {
        return collisionBody;
    }

    public void setCollisionBody(List<CollisionCircle> collisionCircles) {
        for (CollisionCircle c : collisionCircles) {
            getCollisionBody().add(new CollisionCircle(c.getCenterX(), c.getCenterY(), c.getRadius()));
        }
    }

    public void scale(float scale) {
        setSize(getSize().scl(scale));
        for (CollisionCircle circle : collisionBody) {
            circle.setRadius(circle.getRadius() * scale);
            circle.setCenter(circle.getCenterX() * scale,
                    circle.getCenterY() * scale);
        }
    }

    public CollisionTeam getTeam() {
        return team;
    }

    public void setTeam(CollisionTeam team) {
        this.team = team;
        collidesWith.clear();
        switch (team) {
            case ENEMY:
            case ENVIRONMENT:
                collidesWith = EnumSet
                        .of(CollisionTeam.PLAYER_SHIP, CollisionTeam.PLAYER_BULLETS);
                break;
            case ENEMY_BULLETS:
            case ITEMS:
                collidesWith = EnumSet.of(CollisionTeam.PLAYER_SHIP);
                break;
            case PLAYER_SHIP:
                collidesWith = EnumSet.of(CollisionTeam.ENEMY,
                        CollisionTeam.ENVIRONMENT, CollisionTeam.ITEMS,
                        CollisionTeam.ENEMY_BULLETS);
                break;
            case PLAYER_BULLETS:
                collidesWith = EnumSet
                        .of(CollisionTeam.ENEMY, CollisionTeam.ENVIRONMENT);
                break;
        }
    }

    public void onCollision(Body other, CollisionResult collision) {
        lastCollisionTick = collision.tick;
    }

    public boolean isAcceptingCollisions() {
        double sinceCollisionsDisabled = getTime() - collisionsDisabledOn;
        if (sinceCollisionsDisabled <= collisionsDisabledFor)
            return false;
        return acceptsCollisions;
    }

    public void setAcceptsCollisions(boolean acceptsCollisions) {
        this.acceptsCollisions = acceptsCollisions;
    }

    public void disableCollisionsFor(float seconds) {
        collisionsDisabledFor = seconds;
        collisionsDisabledOn = getTime();
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

    public int getLastCollisionTick() {
        return lastCollisionTick;
    }
}
