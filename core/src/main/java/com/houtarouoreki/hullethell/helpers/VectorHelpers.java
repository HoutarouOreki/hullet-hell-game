package com.houtarouoreki.hullethell.helpers;

import com.badlogic.gdx.math.Vector2;

public class VectorHelpers {
    public static Vector2 unitFromDegrees(double degrees) {
        double radians = degrees * 0.017453292519943295769236907684886127134428718885417254560971914401710091146034494436822415696345094822123044925073790592483854692275281012398474218934047117319168245015010769561697553581238605305168789;
        return new Vector2((float)Math.sin(radians), (float)Math.cos(radians));
    }
}
