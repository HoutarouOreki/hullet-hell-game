package com.houtarouoreki.hullethell.graphics;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.configurations.ShipConfiguration;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.screens.PlayScreen;
import org.mini2Dx.core.engine.geom.CollisionCircle;
import org.mini2Dx.core.graphics.Graphics;

public class ShipSprite extends Sprite {
    private final ShipConfiguration c;

    public ShipSprite(ShipConfiguration c) {
        this.c = c;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (!HulletHellGame.getSettings().debugging.getValue())
            return;
        Vector2 size = getRenderSize();
        Vector2 centerPos = getRenderPosition().add(size.scl(0.5f));
        for (CollisionCircle circle : c.collisionCircles) {
            g.drawCircle(centerPos.x + circle.getX() / c.size.x * size.x,
                    centerPos.y - (circle.getY() / c.size.y * size.y),
                    circle.getRadius() / c.size.y * size.y);
        }
    }

    public static void drawCircle(Vector2 position, float radius, Graphics g) {
        Vector2 vp = new Vector2(PlayScreen.viewport.getWidth(), PlayScreen.viewport.getHeight());
        Vector2 renderPos = new Vector2(position.x, World.viewArea.y - position.y).scl(vp).div(World.viewArea);
        g.drawCircle(renderPos.x, renderPos.y, radius / World.viewArea.y * vp.y);
    }
}
