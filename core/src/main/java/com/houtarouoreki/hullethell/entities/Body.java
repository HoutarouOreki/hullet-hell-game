package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.PrimitiveBody;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.Viewport;

import java.util.List;

public class Body extends PrimitiveBody {
    private final List<CollisionCircle> collisionBody;
    private Vector2 acceleration = new Vector2();
    private String textureName;
    private Texture texture;

    public Body(List<CollisionCircle> collisionBody) {
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

    public void render(Graphics g, Viewport vp, Vector2 viewArea) {
        if (texture != null) {
            Vector2 renderSize = getRenderSize(vp, viewArea);
            Vector2 topLeft = new Vector2(getRenderPosition(vp, viewArea)).add(new Vector2(renderSize).scl(-0.5f));
            g.drawTexture(texture, topLeft.x, topLeft.y, renderSize.x, renderSize.y);
        }
        renderCollisionBody(g, vp, viewArea);
    }

    private void renderCollisionBody(Graphics g, Viewport vp, Vector2 viewArea) {
        g.setColor(Color.YELLOW);
        for (CollisionCircle circle : getCollisionBody()) {
            g.drawCircle((getPosition().x + circle.getX()) / viewArea.x * vp.getWidth(),
                    (viewArea.y - (getPosition().y + circle.getY())) / viewArea.y * vp.getHeight(),
                    circle.getRadius() / viewArea.y * vp.getHeight());
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

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
        texture = new Texture(textureName);
    }
}
