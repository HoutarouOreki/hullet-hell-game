package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.PrimitiveBody;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.environment.Updatable;
import com.houtarouoreki.hullethell.graphics.Renderable;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import com.houtarouoreki.hullethell.scripts.ScriptedSection;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Body extends PrimitiveBody implements Renderable, Updatable {
    private final List<CollisionCircle> collisionBody;
    private final Vector2 acceleration = new Vector2();
    private int lastCollisionTick = -1;
    private CollisionTeam team;
    private boolean acceptsCollisions = true;
    private boolean removed;
    private ScriptedSection section;
    private boolean shouldDespawnOOBounds;

    public Body(List<CollisionCircle> collisionBody) {
        this.collisionBody = collisionBody;
    }

    public Body() {
        collisionBody = new ArrayList<CollisionCircle>();
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
        setVelocity(getVelocity().add(new Vector2(getAcceleration()).scl(delta)));
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
                    new Vector2(getPosition()).add(new Vector2(circle.getX(), circle.getY())),
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

    public Vector2 getAcceleration() {
        return acceleration.cpy();
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration.set(acceleration);
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
    }

    public void onCollision(Body other, CollisionResult collision) {
        lastCollisionTick = collision.tick;
    }

    public boolean isAcceptingCollisions() {
        return acceptsCollisions;
    }

    public void setAcceptsCollisions(boolean acceptsCollisions) {
        this.acceptsCollisions = acceptsCollisions;
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
