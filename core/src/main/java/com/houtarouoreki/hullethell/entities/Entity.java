package com.houtarouoreki.hullethell.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.helpers.HealthBarsInfo;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Entity extends Body {
    public final List<Item> items = new ArrayList<>();
    private final HealthBarsInfo healthBarsInfo = new HealthBarsInfo();
    private float health;

    public Entity() {
    }

    public Entity(List<CollisionCircle> collisionBody) {
        super(collisionBody);
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
    public void render(Graphics g) {
        super.render(g);
        //renderHealthBar(g);
    }

    private void renderHealthBar(Graphics g) {
        Vector2 renderSize = getRenderSize();
        Vector2 topLeft = new Vector2(getRenderPosition())
                .mulAdd(renderSize, new Vector2(-0.5f, 0.5f));

        for (int i = 0; i < healthBarsInfo.getHealthBarsAmount(); i++) {
            g.setColor(healthBarsInfo.getColor());
            g.fillRect(topLeft.x, topLeft.y + 10 * i, renderSize.x, 5);
        }
        g.setColor(Color.DARK_GRAY);
        g.fillRect(topLeft.x, topLeft.y + 10 * healthBarsInfo.getHealthBarsAmount(),
                renderSize.x, 5);
        g.setColor(healthBarsInfo.getColor());
        g.fillRect(topLeft.x, topLeft.y + 10 * healthBarsInfo.getHealthBarsAmount(),
                renderSize.x * healthBarsInfo.getLastBarFill(), 5);
    }
}
