package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.graphics.Texture;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionResult;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.graphics.SpriteLayer;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Sprite;

import java.util.Random;

public class Item extends Entity {
    public final String name;
    private final boolean rotatingClockwise;
    private SpriteLayer starLayer;

    public Item(String itemName) {
        name = itemName;
        getCollisionBody().add(new CollisionCircle(0.5f));
        setSize(new Vector2(1));
        addTexture("items/" + itemName + ".png");
        setVelocity(new Vector2(-1, 0));
        rotatingClockwise = new Random().nextBoolean();
        setHealth(1);
        setTeam(CollisionTeam.ITEMS);
    }

    private void addStarAnimation() {
        starLayer = new SpriteLayer();
        spritesLayers.add(starLayer);
        Sprite starSprite = new Sprite(HulletHellGame.getAssetManager().
                get("effects/star.png", Texture.class));
        starLayer.add(starSprite);
        starLayer.offset = new Vector2(-0.07f, 0.08f);
    }

    public void update(float delta) {
        super.update(delta);
        if (spritesLayers.size() == 1)
            addStarAnimation();
        spritesLayers.get(0).rotation += rotatingClockwise ? 80 * delta : -80 * delta;
        starLayer.rotation += 180 * delta;
        double sinScaleY = 2;
        double maxScale = 0.7f;
        starLayer.scale = (float) Math.max(0, Math.sin(getTime()) * sinScaleY - (sinScaleY - maxScale));
    }

    @Override
    public void onCollision(Body other, CollisionResult collision) {
        super.onCollision(other, collision);
        setHealth(0);
    }
}
