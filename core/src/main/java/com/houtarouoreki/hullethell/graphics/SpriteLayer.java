package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.math.Vector2;
import com.houtarouoreki.hullethell.numbers.LoopInt;
import org.mini2Dx.core.graphics.Sprite;

import java.util.ArrayList;

public class SpriteLayer extends ArrayList<Sprite> {
    public final LoopInt frameIndex;
    public float frameLength;
    public Vector2 offset = new Vector2(0, 0);
    public float scale = 1;
    public float rotation = 0;

    public SpriteLayer() {
        frameIndex = new LoopInt(0, 0);
    }

    public float getFPS() {
        return 1 / frameLength;
    }

    public void setFPS(float fps) {
        frameLength = 1 / fps;
    }
}
