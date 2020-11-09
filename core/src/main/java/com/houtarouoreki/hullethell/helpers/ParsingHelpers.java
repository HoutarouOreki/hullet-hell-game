package com.houtarouoreki.hullethell.helpers;

import com.badlogic.gdx.math.Vector2;

public class ParsingHelpers {
    public static Vector2 vector2fromStrings(String x, String y) {
        return new Vector2(Float.parseFloat(x), Float.parseFloat(y));
    }
}
