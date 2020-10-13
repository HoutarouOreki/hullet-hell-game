package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.Viewport;

import java.util.List;

public class Entity extends Body {
    private float health;

    public Entity(AssetManager assetManager, List<CollisionCircle> collisionBody) {
        super(assetManager, collisionBody);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void applyDamage(float damage) {
        this.health -= damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void render(Graphics g, Viewport vp, Vector2 viewArea) {
        super.render(g, vp, viewArea);
        renderHealthBar(g, vp, viewArea);
    }

//    private void renderHealthBar(Graphics g, Viewport vp, Vector2 viewArea) {
//        Vector2 renderSize = getRenderSize(vp, viewArea);
//        Vector2 topLeft = new Vector2(getRenderPosition(vp, viewArea)).mulAdd(getRenderSize(vp, viewArea), new Vector2(-0.5f, 0.5f));
//        for (int i = 0; i < (int)(health - 1) / 10 + 1; i++) {
//            g.setColor(Color.DARK_GRAY);
//            g.fillRect(topLeft.x, topLeft.y + 10 * i, renderSize.x, 5);
//            g.setColor(Color.RED);
//            float fill = i == (int)(health - 1) / 10 ? renderSize.x * (health - i * 10) / 10f : renderSize.x;
//            g.fillRect(topLeft.x, topLeft.y + 10 * i, fill, 5);
//        }
//    }

    private void renderHealthBar(Graphics g, Viewport vp, Vector2 viewArea) {
        float remainingHealthToDraw = health;
        Vector2 renderSize = getRenderSize(vp, viewArea);
        Vector2 topLeft = new Vector2(getRenderPosition(vp, viewArea)).mulAdd(getRenderSize(vp, viewArea), new Vector2(-0.5f, 0.5f));
        int i = 1;
        int purpleHealthBarsAmount = EntityHelpers.bigHealthBarsAmount(health);
        while (i <= purpleHealthBarsAmount) {
            g.setColor(Color.PURPLE);
            g.fillRect(topLeft.x, topLeft.y + 10 * i, renderSize.x, 5);
            i++;
            remainingHealthToDraw -= 40;
        }
        int totalHealthBarsAmount = purpleHealthBarsAmount + EntityHelpers.smallHealthBarsAmount(health);
        while (i < totalHealthBarsAmount) {
            g.setColor(Color.RED);
            g.fillRect(topLeft.x, topLeft.y + 10 * i, renderSize.x, 5);
            i++;
            remainingHealthToDraw -= 10;
        }
        g.setColor(Color.DARK_GRAY);
        g.fillRect(topLeft.x, topLeft.y + 10 * i, renderSize.x, 5);
        g.setColor(Color.RED);
        g.fillRect(topLeft.x, topLeft.y + 10 * i, renderSize.x * remainingHealthToDraw / 10, 5);
    }
}
