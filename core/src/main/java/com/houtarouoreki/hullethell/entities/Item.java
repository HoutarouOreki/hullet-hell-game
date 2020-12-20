package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;

public class Item extends Body {
    public final String name;

    public Item(String itemName) {
        name = itemName;
        new CollisionCircle(0.5f);
        setSize(new Vector2(1, 1));
        setTextureName("items/" + itemName + ".png");
        setVelocity(new Vector2(-1, 0));
    }
}
