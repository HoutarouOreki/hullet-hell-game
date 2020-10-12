package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.PrimitiveBody;
import com.houtarouoreki.hullethell.environment.collisions.CollisionResult;
import com.houtarouoreki.hullethell.environment.collisions.CollisionTeam;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.Viewport;

import java.util.List;

public class Body extends PrimitiveBody {
    private final List<CollisionCircle> collisionBody;
    private CollisionTeam team;
    private Vector2 acceleration = new Vector2();
    private boolean acceptsCollisions = true;

    public Body(AssetManager assetManager, List<CollisionCircle> collisionBody) {
        super(assetManager);
        this.collisionBody = collisionBody;
    }

    @Override
    public void physics(float delta, Vector2 viewArea) {
        super.physics(delta, viewArea);
        getVelocity().add(new Vector2(getAcceleration()).scl(delta));
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
    public void render(Graphics g, Viewport vp, Vector2 viewArea) {
        super.render(g, vp, viewArea);
        renderCollisionBody(g, vp, viewArea);
    }

    private void renderCollisionBody(Graphics g, Viewport vp, Vector2 viewArea) {
        g.setColor(Color.YELLOW);
        for (CollisionCircle circle : getCollisionBody()) {
            Vector2 renderPos = translateToRenderPosition(new Vector2(getPosition()).add(new Vector2(circle.getX(), circle.getY())), vp, viewArea);
            g.drawCircle(renderPos.x, renderPos.y, circle.getRadius() / viewArea.y * vp.getHeight());
        }
    }

    public List<CollisionCircle> getCollisionBody() {
        return collisionBody;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public CollisionTeam getTeam() {
        return team;
    }

    public void setTeam(CollisionTeam team) {
        this.team = team;
    }

    public void onCollision(Body other, CollisionResult collision) { }

    public boolean isAcceptingCollisions() {
        return acceptsCollisions;
    }

    public void setAcceptsCollisions(boolean acceptsCollisions) {
        this.acceptsCollisions = acceptsCollisions;
    }
}
