package com.houtarouoreki.hullethell.helpers;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class ParsingHelpers {
    public static Vector2 vector2fromStrings(String x, String y) {
        return new Vector2(Float.parseFloat(x), Float.parseFloat(y));
    }

    public static Interpolation getInterpolationFromString(String s) {
        switch (s) {
            case "linear":
                return Interpolation.linear;
            case "smooth":
                return Interpolation.smooth;
            case "smooth2":
                return Interpolation.smooth2;
            case "smoother":
                return Interpolation.smoother;
            case "fade":
                return Interpolation.fade;
            case "pow2":
                return Interpolation.pow2;
            case "pow2In":
                return Interpolation.pow2In;
            case "pow2Out":
                return Interpolation.pow2Out;
            case "pow2InInverse":
                return Interpolation.pow2InInverse;
            case "pow2OutInverse":
                return Interpolation.pow2OutInverse;
            case "pow3":
                return Interpolation.pow3;
            case "pow3In":
                return Interpolation.pow3In;
            case "pow3Out":
                return Interpolation.pow3Out;
            case "pow3InInverse":
                return Interpolation.pow3InInverse;
            case "pow3OutInverse":
                return Interpolation.pow3OutInverse;
            case "pow4":
                return Interpolation.pow4;
            case "pow4In":
                return Interpolation.pow4In;
            case "pow4Out":
                return Interpolation.pow4Out;
            case "pow5":
                return Interpolation.pow5;
            case "pow5In":
                return Interpolation.pow5In;
            case "pow5Out":
                return Interpolation.pow5Out;
            case "sine":
                return Interpolation.sine;
            case "sineIn":
                return Interpolation.sineIn;
            case "sineOut":
                return Interpolation.sineOut;
            case "exp10":
                return Interpolation.exp10;
            case "exp10In":
                return Interpolation.exp10In;
            case "exp10Out":
                return Interpolation.exp10Out;
            case "exp5":
                return Interpolation.exp5;
            case "exp5In":
                return Interpolation.exp5In;
            case "exp5Out":
                return Interpolation.exp5Out;
            case "circle":
                return Interpolation.circle;
            case "circleIn":
                return Interpolation.circleIn;
            case "circleOut":
                return Interpolation.circleOut;
            case "elastic":
                return Interpolation.elastic;
            case "elasticIn":
                return Interpolation.elasticIn;
            case "elasticOut":
                return Interpolation.elasticOut;
            case "swing":
                return Interpolation.swing;
            case "swingIn":
                return Interpolation.swingIn;
            case "swingOut":
                return Interpolation.swingOut;
            case "bounce":
                return Interpolation.bounce;
            case "bounceIn":
                return Interpolation.bounceIn;
            case "bounceOut":
                return Interpolation.bounceOut;
            default:
                throw new Error("Could not find interpolation \"" + s + "\"");
        }
    }
}
