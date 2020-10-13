package com.houtarouoreki.hullethell.helpers;

public class EntityHelpers {
    public static int bigHealthBarsAmount(float health) {
        return (int) Math.signum(health) * ((healthTemp(health) - 1) / 4);
    }

    public static int smallHealthBarsAmount(float health) {
        return (int) Math.signum(health) * (((healthTemp(health) - 1) % 4) + 1);
    }

    private static int healthTemp(float health) {
        return (int) (Math.ceil(health - 1) / 10) + 1;
    }
}
