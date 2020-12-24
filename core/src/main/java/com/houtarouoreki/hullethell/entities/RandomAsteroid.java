package com.houtarouoreki.hullethell.entities;

import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.Random;

public class RandomAsteroid extends Entity {
    private final float scale;
    private final boolean clockwiseRotation;

    public RandomAsteroid(float scale) {
        super();
        this.scale = scale;
        setShouldDespawnOOBounds(true);
        this.setSize(new Vector2(1, 1));
        setTeam(CollisionTeam.ENVIRONMENT);
        getCollisionBody().add(new CollisionCircle(0, 0, 0.5f));
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
        collidesWith.add(CollisionTeam.ENVIRONMENT);
        clockwiseRotation = random.nextBoolean();
    }

    private float getMaxHealth() {
        return 10 * scale * scale;
    }

    private void setTexture(float scale) {
        if (scale >= 2f)
            addTexture("environmentals/asteroid-large.png");
        else if (scale >= 1f)
            addTexture("environmentals/asteroid-medium.png");
        else if (scale >= 0.75f)
            addTexture("environmentals/asteroid-small.png");
        else
            addTexture("environmentals/asteroid-tiny.png");
    }

    @Override
    public void applyDamage(float damage) {
        super.applyDamage(damage);
        if (scale < 2)
            return;
        spritesLayers.clear();
        float percent = getHealth() / getMaxHealth();
        if (percent < 0.3)
            addTexture("environmentals/asteroid-large-dmg3.png");
        else if (percent < 0.6)
            addTexture("environmentals/asteroid-large-dmg2.png");
        else if (percent < 0.9)
            addTexture("environmentals/asteroid-large-dmg1.png");
        else
            addTexture("environmentals/asteroid-large.png");

    }

    public RandomAsteroid() {
        this(new Random().nextFloat() * 2.5f + 0.5f);
    }

    public void update(float delta) {
        super.update(delta);
        rotation += 40 * delta / scale / scale * (clockwiseRotation ? 1 : -1);
        if (spritesLayers.isEmpty())
            setTexture(scale);
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (other instanceof RandomAsteroid) {
            System.out.println();
        }
        if (other instanceof Entity)
            ((Entity) other).applyDamage(scale * getVelocity().len2());
    }
}
