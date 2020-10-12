package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.environment.collisions.CollisionResult;
import org.mini2Dx.core.engine.geom.CollisionCircle;

import java.util.ArrayList;

public class Bullet extends Entity {
    private final int damage = 2;

    public Bullet(AssetManager assetManager) {
        super(assetManager, new ArrayList<CollisionCircle>() {{
            add(new CollisionCircle(0, 0, 0.3f));
        }});
        setTextureName("bullet1.png");
        sprite.setColor(Color.RED);
        setSize(new Vector2(0.6f, 0.6f));
        setHealth(damage);
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (other instanceof Entity) {
            ((Entity) other).applyDamage(damage);
        }
    }
}
