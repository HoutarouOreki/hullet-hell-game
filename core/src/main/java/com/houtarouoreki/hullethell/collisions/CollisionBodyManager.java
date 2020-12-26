package com.houtarouoreki.hullethell.collisions;

import com.houtarouoreki.hullethell.entities.Body;
import com.houtarouoreki.hullethell.helpers.BasicObjectListener;
import com.houtarouoreki.hullethell.numbers.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class CollisionBodyManager {
    public final Body body;
    private final List<CollisionBodyManager> dontCollideWith = new ArrayList<>();
    private final List<Circle> collisionBody = new ArrayList<>();
    private final List<BasicObjectListener<CollisionResult>> collisionListeners = new ArrayList<>();
    public EnumSet<CollisionTeam> collidesWith = EnumSet.noneOf(CollisionTeam.class);
    private CollisionTeam team;
    private int lastCollisionTick = -1;
    private boolean acceptsCollisions = true;
    private float collisionsDisabledFor;
    private double collisionsDisabledOn;
    private float farthestPointDistance;

    public CollisionBodyManager(Body body) {
        this.body = body;
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

    public List<Circle> getCollisionBodyCopy() {
        return Collections.unmodifiableList(collisionBody);
    }

    public List<Circle> getCollisionBodyCopy(boolean withAbsolutePositions) {
        if (!withAbsolutePositions)
            return getCollisionBodyCopy();

        List<Circle> list = new ArrayList<>();
        for (Circle c : collisionBody)
            list.add(c.add(body.getPosition()));
        return list;
    }

    public void setCollisionBody(List<Circle> circles) {
        collisionBody.clear();
        farthestPointDistance = 0;
        for (Circle c : circles) {
            collisionBody.add(c);
            float distance = c.longestDistanceTo(Vector2.ZERO);
            if (distance > farthestPointDistance)
                farthestPointDistance = distance;
        }
    }

    public float getFarthestPointDistance() {
        return farthestPointDistance;
    }

    public boolean isAcceptingCollisions() {
        double sinceCollisionsDisabled = body.getTime() - collisionsDisabledOn;
        if (sinceCollisionsDisabled <= collisionsDisabledFor)
            return false;
        return acceptsCollisions;
    }

    public boolean isAcceptingCollisions(int onTick) {
        return isAcceptingCollisions() && getLastCollisionTick() != onTick;
    }

    public void setAcceptsCollisions(boolean acceptsCollisions) {
        this.acceptsCollisions = acceptsCollisions;
    }

    public void disableCollisionsFor(float seconds) {
        collisionsDisabledFor = seconds;
        collisionsDisabledOn = body.getTime();
    }

    public int getLastCollisionTick() {
        return lastCollisionTick;
    }

    public void onCollision(CollisionResult collision) {
        lastCollisionTick = collision.tick;
        for (BasicObjectListener<CollisionResult> listener : collisionListeners)
            listener.onAction(collision);
    }

    public void addCollisionListener(BasicObjectListener<CollisionResult> collisionListener) {
        collisionListeners.add(collisionListener);
    }

    public void disableCollisionsWith(Body body) {
        dontCollideWith.add(body.collisionBodyManager);
    }

    public boolean collidesWith(CollisionBodyManager other) {
        return isAcceptingCollisions() && collidesWith.contains(other.getTeam())
                && !dontCollideWith.contains(other);
    }

    public boolean collidesWith(CollisionBodyManager other, int onTick) {
        return isAcceptingCollisions(onTick) && collidesWith.contains(other.getTeam())
                && !dontCollideWith.contains(other);
    }

    public void disableCollisionsWith(List<? extends Body> bodies) {
        for (Body body : bodies)
            disableCollisionsWith(body);
    }
}
