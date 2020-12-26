package com.houtarouoreki.hullethell.graphics;

import com.houtarouoreki.hullethell.PrimitiveBody;
import com.houtarouoreki.hullethell.entities.Entity;
import com.houtarouoreki.hullethell.environment.Updatable;
import org.mini2Dx.core.graphics.Sprite;

import java.util.HashMap;

public class BodySpriteManager implements Updatable {
    private final PrimitiveBody body;
    private final HashMap<Integer, Float> minimumHealthForDamageStage = new HashMap<>();
    public SpriteInfo.Size size = SpriteInfo.Size.DEFAULT;
    private float lastTickHealth = -10;

    public BodySpriteManager(PrimitiveBody body) {
        this.body = body;
    }

    public void setDamageStageMinHealth(int damageStage, float minHealth) {
        minimumHealthForDamageStage.put(damageStage, minHealth);
    }

    public void update(float delta) {
        if (body.spriteInfo == null)
            return;
        if (body.spritesLayers.isEmpty())
            setSprite();
        if (!(body instanceof Entity))
            return;
        Entity entity = (Entity) body;
        updateDamageSprite();
        lastTickHealth = entity.getHealth();
    }

    private void setSprite() {
        if (body.spriteInfo.framesCount.get(size) <= 1)
            body.addTexture(body.spriteInfo.getPath(size));
        else
            body.addAnimation(body.spriteInfo.getPathWithoutExtension(),
                    body.spriteInfo.framesCount.get(size),
                    body.spriteInfo.framesCount.get(size));
    }

    private void updateDamageSprite() {
        Entity entity = (Entity) body;
        if (entity.spriteInfo.damageTexturesCount.getOrDefault(size, 0) == 0 || minimumHealthForDamageStage.isEmpty())
            return;
        int currentDamageStage = getDamageStage(entity.getHealth());
        if (getDamageStage(lastTickHealth) != currentDamageStage) {
            SpriteLayer spriteLayer = entity.spritesLayers.get(0);
            spriteLayer.clear();
            spriteLayer.add(new Sprite(entity.spriteInfo.getTexture(size, currentDamageStage)));
        }
    }

    private int getDamageStage(float health) {
        int lowestStage = body.spriteInfo.damageTexturesCount.getOrDefault(size, body.spriteInfo.damageTexturesCount.get(SpriteInfo.Size.DEFAULT));
        for (Integer stageNumber : minimumHealthForDamageStage.keySet()) {
            float minHealthForThisStage = minimumHealthForDamageStage.get(stageNumber);
            if (health >= minHealthForThisStage)
                lowestStage = Math.min(lowestStage, stageNumber);
        }
        return lowestStage;
    }
}
