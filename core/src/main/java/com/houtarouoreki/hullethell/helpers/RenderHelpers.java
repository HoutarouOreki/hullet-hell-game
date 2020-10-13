package com.houtarouoreki.hullethell.helpers;

import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.Viewport;

public class RenderHelpers {

    public static Vector2 translateToRenderPosition(Vector2 position, Viewport vp, Vector2 viewArea) {
        return new Vector2(position.x, viewArea.y - position.y).scl(vp.getWidth(), vp.getHeight()).scl(1 / viewArea.x, 1 / viewArea.y);
    }

    public static void drawWorldCircle(Vector2 worldPosition, float radius, Graphics g, Viewport vp, Vector2 viewArea) {
        Vector2 renderPos = translateToRenderPosition(worldPosition, vp, viewArea);
        g.drawCircle(renderPos.x, renderPos.y, radius / viewArea.y * vp.getHeight());
    }

    public static void fillWorldCircle(Vector2 worldPosition, float radius, Graphics g, Viewport vp, Vector2 viewArea) {
        Vector2 renderPos = translateToRenderPosition(worldPosition, vp, viewArea);
        g.fillCircle(renderPos.x, renderPos.y, radius / viewArea.y * vp.getHeight());
    }
}
