package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.configurations.BodyConfiguration;
import com.houtarouoreki.hullethell.environment.collisions.CollisionResult;

import java.util.Random;

public class Bullet extends Entity {
    private Ship source;

    public Bullet(AssetManager assetManager, String configurationName) {
        super(assetManager);
        String path = "bullets/" + configurationName;
        BodyConfiguration c = assetManager.get(path + ".cfg", BodyConfiguration.class);
        setTextureName(path + ".png");
        setHealth(c.getMaxHealth());
        setSize(new Vector2(c.getSize()));
        setCollisionBody(c.getCollisionCircles());
        setShouldDespawnOOBounds(true);
        Color color;
        switch (new Random().nextInt(10)) {
            case 0:
                color = Color.RED;
                break;
            case 1:
                color = Color.YELLOW;
                break;
            case 2:
                color = Color.GREEN;
                break;
            case 3:
                color = Color.CYAN;
                break;
            case 4:
                color = Color.BLUE;
                break;
            case 5:
                color = Color.PINK;
                break;
            case 6:
                color = Color.PURPLE;
                break;
            case 7:
                color = Color.CORAL;
                break;
            case 8:
                color = Color.GOLD;
                break;
            default:
                color = Color.MAGENTA;
                break;
        }
        sprite.setColor(color);
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        if (other instanceof Entity) {
            ((Entity) other).applyDamage(2);
        }
    }

    public void setSource(Ship ship) {
        source = ship;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        source.unregisterBullet(this);
    }
}
