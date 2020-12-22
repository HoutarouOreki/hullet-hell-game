package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.environment.World;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.Random;

public class RandomAsteroid extends Entity {
    private final float scale;
    private final boolean clockwiseRotation;

    public RandomAsteroid(float scale) {
        super();
        this.scale = scale;
        setShouldDespawnOOBounds(true);
        setSize(new Vector2(1, 1));
        setTeam(CollisionTeam.ENVIRONMENT);
        getCollisionBody().add(new CollisionCircle(0, 0, 0.5f));
        Random random = new Random();
        Vector2 velocity = new Vector2(
                -(random.nextFloat() * 6 + 3),
                random.nextFloat() * 2.2f - 1.1f
        );
        setHealth(10 * scale * scale);
        setVelocity(velocity.scl(1 / scale));
        scale(scale);
        Vector2 startingPosition = new Vector2(
                1,
                random.nextFloat()
        ).scl(World.viewArea).add(getSize().scl(new Vector2(0.5f, 0)));
        setPosition(startingPosition);
        collidesWith.add(CollisionTeam.ENVIRONMENT);
        clockwiseRotation = random.nextBoolean();
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

    public RandomAsteroid() {
        this(new Random().nextFloat() * 2.5f + 0.5f);
    }

    public void update(float delta) {
        super.update(delta);
        if (spritesLayers.isEmpty())
            setTexture(scale);
        spritesLayers.get(0).rotation += 40 * delta / scale / scale * (clockwiseRotation ? 1 : -1);
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
