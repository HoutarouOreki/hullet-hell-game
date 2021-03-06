package com.houtarouoreki.hullethell.helpers;

import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.numbers.Vector2;
import com.houtarouoreki.hullethell.screens.PlayScreen;
import org.mini2Dx.core.graphics.Graphics;

public class RenderHelpers {
    public static Vector2 translateToRenderPosition(Vector2 position) {
        return new Vector2(position.x, World.viewArea.y - position.y).scl(getViewport()).div(World.viewArea);
    }

    public static Vector2 translateToRenderSize(Vector2 size) {
        return size.scl(getViewport()).div(World.viewArea);
    }

    public static void drawWorldCircle(Vector2 worldPosition, float radius, Graphics g) {
        Vector2 vp = new Vector2(PlayScreen.viewport.getWidth(), PlayScreen.viewport.getHeight());
        Vector2 renderPos = translateToRenderPosition(worldPosition);
        g.drawCircle(renderPos.x, renderPos.y, radius / World.viewArea.y * vp.y);
    }

    public static void fillWorldCircle(Vector2 worldPosition, float radius, Graphics g) {
        Vector2 vp = new Vector2(PlayScreen.viewport.getWidth(), PlayScreen.viewport.getHeight());
        Vector2 renderPos = translateToRenderPosition(worldPosition);
        g.fillCircle(renderPos.x, renderPos.y, radius / World.viewArea.y * vp.y);
    }

    public static Vector2 getViewport() {
        return new Vector2(PlayScreen.viewport.getWidth(), PlayScreen.viewport.getHeight());
    }
}
