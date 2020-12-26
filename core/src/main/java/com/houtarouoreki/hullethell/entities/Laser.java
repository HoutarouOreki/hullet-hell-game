package com.houtarouoreki.hullethell.entities;

import com.houtarouoreki.hullethell.bindables.Bindable;
import com.houtarouoreki.hullethell.collisions.Circle;
import com.houtarouoreki.hullethell.collisions.CollisionBodyManager;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.numbers.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Laser extends Body {
    public float damage;
    private Bindable<Float> length;
    private Bindable<Float> width;
    private Bindable<Float> rotationSpeed;
    public float rotation;

    public Laser() {
        getCollisionBodyManager().setTeam(CollisionTeam.ENVIRONMENT);
    }

    @Override
    protected CollisionBodyManager generateCollisionBodyManager() {
        length = new Bindable<>(0f);
        width = new Bindable<>(0f);
        rotationSpeed = new Bindable<>(0f);
        return new LaserCollisionBodyManager(this);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        rotation += rotationSpeed.getValue() * 360 * delta;
    }

    @Override
    protected void onCollision(CollisionResult collision) {
        super.onCollision(collision);
        if (collision.other instanceof Entity)
            ((Entity) collision.other).applyDamage(damage);
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

    public float getRotationSpeed() {
        return rotationSpeed.getValue();
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed.setValue(rotationSpeed);
    }

    public class LaserCollisionBodyManager extends CollisionBodyManager {
        private boolean collisionBodyInvalid = true;

        public LaserCollisionBodyManager(Laser laser) {
            super(laser);
            length.addListener(this::onLaserPropertyUpdated);
            width.addListener(this::onLaserPropertyUpdated);
            rotationSpeed.addListener(this::onLaserPropertyUpdated);
        }

        private void onLaserPropertyUpdated(float oldValue, float newValue) {
            if (oldValue != newValue)
                collisionBodyInvalid = true;
        }

        private void generateCollisionCircles() {
            List<Circle> newCollisionBody = new ArrayList<>();
            float highestY = 0;
            float radius = getWidth() * 0.5f;
            do {
                float circleCenterY = highestY + radius;
                newCollisionBody.add(new Circle(new Vector2(0, circleCenterY), getWidth()));
                highestY += getWidth();
            } while (highestY < getLength());
            setCollisionBody(newCollisionBody);
            collisionBodyInvalid = false;
            farthestPointDistance = getLength();
            System.out.println("h");
        }

        @Override
        public List<Circle> getCollisionBodyCopy(boolean withAbsolutePositions) {
            if (collisionBodyInvalid)
                generateCollisionCircles();
            List<Circle> rotatedCircles = new ArrayList<>();
            for (Circle circle : super.getCollisionBodyCopy(false)) {
                Vector2 rotatedPosition = circle.position.rotated(rotation, true);
                if (withAbsolutePositions)
                    rotatedPosition = rotatedPosition.add(getPosition());
                Circle rotatedCircle = new Circle(rotatedPosition, circle.radius);
                rotatedCircles.add(rotatedCircle);
            }
            return rotatedCircles;
        }

        @Override
        public boolean canCollideWith(CollisionBodyManager other) {
            if (collisionBodyInvalid)
                generateCollisionCircles();
            return !(other instanceof LaserCollisionBodyManager) && super.canCollideWith(other);
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
    }
}
