package com.houtarouoreki.hullethell.entities;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.Circle;
import com.houtarouoreki.hullethell.collisions.CollisionBodyManager;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.SpriteInfo;
import com.houtarouoreki.hullethell.numbers.Vector2;

import java.util.Collections;
import java.util.Random;

public class RandomAsteroid extends Entity {
    private final float scale;
    private final boolean clockwiseRotation;
    public RandomAsteroid parent;

    public RandomAsteroid(float scale) {
        super();
        this.scale = scale;
        setShouldDespawnOOBounds(true);
        this.setSize(new Vector2(1, 1));
        getCollisionBodyManager().setTeam(CollisionTeam.ENVIRONMENT);
        getCollisionBodyManager().setCollisionBody(Collections
                .singletonList(new Circle(Vector2.ZERO, 0.5f)));
        Random random = new Random();
        Vector2 velocity = new Vector2(
                -(random.nextFloat() * 6 + 3),
                random.nextFloat() * 2.2f - 1.1f
        );
        setHealth(getMaxHealth());
        this.setVelocity(velocity.div(scale));
        scale(scale);
        setPosition(new Vector2(
                1,
                random.nextFloat()
        ).scl(World.viewArea).add(getSize().scl(new Vector2(0.5f, 0))));
        clockwiseRotation = random.nextBoolean();
        setTexture(scale);
    }

    public RandomAsteroid() {
        this(new Random().nextFloat() * 2f + 0.5f);
    }

    private float getMaxHealth() {
        return 10 * scale * scale;
    }

    private void setTexture(float scale) {
        spriteInfo = HulletHellGame.getAssetManager()
                .get("environmentals/asteroid-sprite.hhc", SpriteInfo.class);
        if (scale >= 1.8f)
            bodySpriteManager.size = SpriteInfo.Size.LARGE;
        else if (scale >= 1f)
            bodySpriteManager.size = SpriteInfo.Size.MEDIUM;
        else if (scale >= 0.75f)
            bodySpriteManager.size = SpriteInfo.Size.SMALL;
        else
            bodySpriteManager.size = SpriteInfo.Size.TINY;
        bodySpriteManager.setDamageStageMinHealth(0, getMaxHealth() * 0.8f);
        bodySpriteManager.setDamageStageMinHealth(1, getMaxHealth() * 0.5f);
        bodySpriteManager.setDamageStageMinHealth(2, getMaxHealth() * 0.2f);
        bodySpriteManager.setDamageStageMinHealth(3, getMaxHealth() * 0.1f);
    }

    public void update(float delta) {
        super.update(delta);
        rotation += 40 * delta / scale / scale * (clockwiseRotation ? 1 : -1);
    }

    @Override
    public void onCollision(CollisionResult collision) {
        super.onCollision(collision);
        if (collision.other instanceof Entity)
            ((Entity) collision.other).applyDamage(scale * getVelocity().len2());
    }

    @Override
    protected CollisionBodyManager generateCollisionBodyManager() {
        return new AsteroidCollisionBodyManager(this);
    }

    private class AsteroidCollisionBodyManager extends CollisionBodyManager {
        private final RandomAsteroid asteroid;

        public AsteroidCollisionBodyManager(RandomAsteroid asteroid) {
            super(asteroid);
            this.asteroid = asteroid;
        }

        @Override
        public boolean canCollideWith(CollisionBodyManager other) {
            if (other instanceof AsteroidCollisionBodyManager) {
                RandomAsteroid otherAsteroid = ((AsteroidCollisionBodyManager) other).asteroid;
                if (otherAsteroid.parent != null && otherAsteroid.parent == parent)
                    return false;
            }
            return super.canCollideWith(other);
        }
    }
}
