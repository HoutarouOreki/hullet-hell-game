package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.helpers.HealthBarsInfo;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.Viewport;

import java.util.List;

public class Entity extends Body {
    private final HealthBarsInfo healthBarsInfo;
    private float health;

    public Entity(AssetManager assetManager, List<CollisionCircle> collisionBody) {
        super(assetManager, collisionBody);
        healthBarsInfo = new HealthBarsInfo();
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
        healthBarsInfo.update(health);

    }

    public void applyDamage(float damage) {
        this.health -= damage;
        healthBarsInfo.update(health);
    }

    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void render(Graphics g, Viewport vp, Vector2 viewArea) {
        super.render(g, vp, viewArea);
        renderHealthBar(g, vp, viewArea);
    }

    private void renderHealthBar(Graphics g, Viewport vp, Vector2 viewArea) {
        Vector2 renderSize = getRenderSize(vp, viewArea);
        Vector2 topLeft = new Vector2(getRenderPosition(vp, viewArea)).mulAdd(getRenderSize(vp, viewArea), new Vector2(-0.5f, 0.5f));

        for (int i = 0; i < healthBarsInfo.getHealthBarsAmount(); i++) {
            g.setColor(healthBarsInfo.getColor());
            g.fillRect(topLeft.x, topLeft.y + 10 * i, renderSize.x, 5);
        }
        g.setColor(Color.DARK_GRAY);
        g.fillRect(topLeft.x, topLeft.y + 10 * healthBarsInfo.getHealthBarsAmount(), renderSize.x, 5);
        g.setColor(healthBarsInfo.getColor());
        g.fillRect(topLeft.x, topLeft.y + 10 * healthBarsInfo.getHealthBarsAmount(), renderSize.x * healthBarsInfo.getLastBarFill(), 5);
    }
}
