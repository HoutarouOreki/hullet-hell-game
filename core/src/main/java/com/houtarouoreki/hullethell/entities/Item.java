package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Sprite;

import java.util.Random;

public class Item extends Body {
    public final String name;
    private final boolean rotatingClockwise;

    public Item(String itemName) {
        name = itemName;
        new CollisionCircle(0.5f);
        setSize(new Vector2(1, 1));
        setTextureName("items/" + itemName + ".png");
        setVelocity(new Vector2(-1, 0));
        rotatingClockwise = new Random().nextBoolean();
    }

    public void update(float delta) {
        super.update(delta);
        for (Sprite sprite : sprites) {
            sprite.rotate(rotatingClockwise ? 1 : -1);
        }
    }
}
