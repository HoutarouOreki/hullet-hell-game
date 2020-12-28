package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.graphics.Texture;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.collisions.Circle;
import com.houtarouoreki.hullethell.collisions.CollisionBodyManager;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.helpers.RenderHelpers;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Laser extends Body {
    private final Sprite headSprite;
    private final Sprite bodySprite;
    private final Sprite tailSprite;
    public float damage;
    public float rotation;
    private Bindable<Float> length;
    private Bindable<Float> width;
    private Bindable<Float> rotationDuration;

    public Laser() {
        getCollisionBodyManager().setTeam(CollisionTeam.ENVIRONMENT);
        String prefix = "lasers/";
        String name = "laser";
        Texture bodyTexture = HulletHellGame.getAssetManager().get(prefix + name + ".png");
        bodySprite = new Sprite(bodyTexture);
        if (HulletHellGame.getAssetManager().isLoaded(prefix + name + "-head.png")) {
            Texture headTexture = HulletHellGame.getAssetManager().get(prefix + name + "-head.png");
            headSprite = new Sprite(headTexture);
        } else headSprite = null;
        if (HulletHellGame.getAssetManager().isLoaded(prefix + name + "-tail.png")) {
            Texture headTexture = HulletHellGame.getAssetManager().get(prefix + name + "-tail.png");
            tailSprite = new Sprite(headTexture);
        } else tailSprite = null;
        length.addListener(this::onSizeChanged);
        width.addListener(this::onSizeChanged);
    }

    private void onSizeChanged(Float oldValue, Float newValue) {
        setSize(new Vector2(width.getValue(), 0));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        rotation += 360 * delta / rotationDuration.getValue();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        Vector2 size = RenderHelpers
                .translateToRenderSize(new Vector2(width.getValue(), length.getValue()));
        bodySprite.setSize(size.x, 0);
        bodySprite.setOriginCenter();
        bodySprite.setOriginBasedPosition(getRenderPosition().x, getRenderPosition().y);
        bodySprite.setRotation(rotation + 180);
        bodySprite.setSize(size.x, size.y);
        g.drawSprite(bodySprite);

        Vector2 headSize = RenderHelpers.translateToRenderSize(new Vector2(1));
        if (headSprite != null) {
            headSprite.setSize(headSize.x, headSize.y);
            headSprite.setOriginCenter();
            headSprite.setOriginBasedPosition(getRenderPosition().x, getRenderPosition().y);
            headSprite.setRotation(rotation + 180);
            g.drawSprite(headSprite);
        }

        Vector2 tailSize = RenderHelpers.translateToRenderSize(new Vector2(width.getValue()));
        if (tailSprite != null) {
            tailSprite.setSize(tailSize.x, tailSize.y);
            tailSprite.setOrigin(tailSize.x * 0.5f, -length.getValue());
            tailSprite.setOriginBasedPosition(getRenderPosition().x, getRenderPosition().y);
            tailSprite.setRotation(rotation + 180);
            g.drawSprite(tailSprite);
        }
    }

    @Override
    protected void onCollision(CollisionResult collision) {
        super.onCollision(collision);
        if (collision.other instanceof Entity)
            ((Entity) collision.other).applyDamage(damage);
    }

    @Override
    protected CollisionBodyManager generateCollisionBodyManager() {
        length = new Bindable<>(0f);
        width = new Bindable<>(0f);
        rotationDuration = new Bindable<>(0f);
        return new LaserCollisionBodyManager(this);
    }

    public float getLength() {
        return length.getValue();
    }

    public void setLength(float length) {
        this.length.setValue(length);
    }

    public float getWidth() {
        return width.getValue();
    }

    public void setWidth(float width) {
        this.width.setValue(width);
    }

    public float getRotationDuration() {
        return rotationDuration.getValue();
    }

    public void setRotationDuration(float rotationDuration) {
        this.rotationDuration.setValue(rotationDuration);
    }

    public class LaserCollisionBodyManager extends CollisionBodyManager {
        private boolean collisionBodyInvalid = true;

        public LaserCollisionBodyManager(Laser laser) {
            super(laser);
            length.addListener(this::onLaserPropertyUpdated);
            width.addListener(this::onLaserPropertyUpdated);
            rotationDuration.addListener(this::onLaserPropertyUpdated);
        }

        private void onLaserPropertyUpdated(float oldValue, float newValue) {
            if (oldValue != newValue)
                collisionBodyInvalid = true;
        }

        @Override
        public List<Circle> getCollisionBodyCopy(boolean withAbsolutePositions) {
            if (collisionBodyInvalid)
                generateCollisionCircles();
            List<Circle> baseCollisionBody = super.getCollisionBodyCopy(false);
            List<Circle> rotatedCircles = new ArrayList<>(baseCollisionBody.size());
            for (Circle circle : baseCollisionBody) {
                Vector2 rotatedPosition = circle.position.rotated(rotation, true);
                if (withAbsolutePositions)
                    rotatedPosition = rotatedPosition.add(getPosition());
                Circle rotatedCircle = new Circle(rotatedPosition, circle.radius);
                rotatedCircles.add(rotatedCircle);
            }
            return rotatedCircles;
        }

        private void generateCollisionCircles() {
            List<Circle> newCollisionBody = new ArrayList<>((int) (getLength() / getWidth() + 3));
            float highestY = 0;
            float radius = getWidth() * 0.5f;
            do {
                float circleCenterY = highestY + radius;
                newCollisionBody.add(new Circle(new Vector2(0, circleCenterY), radius));
                highestY += getWidth();
            } while (highestY < getLength());
            setCollisionBody(newCollisionBody);
            collisionBodyInvalid = false;
            farthestPointDistance = getLength();
        }

        @Override
        public float getFarthestPointDistance() {
            if (collisionBodyInvalid)
                generateCollisionCircles();
            return super.getFarthestPointDistance();
        }

        @Override
        public boolean isAcceptingCollisions() {
            if (collisionBodyInvalid)
                generateCollisionCircles();
            return super.isAcceptingCollisions();
        }

        @Override
        public boolean canCollideWith(CollisionBodyManager other) {
            if (collisionBodyInvalid)
                generateCollisionCircles();
            return !(other instanceof LaserCollisionBodyManager) && super.canCollideWith(other);
        }
    }
}
