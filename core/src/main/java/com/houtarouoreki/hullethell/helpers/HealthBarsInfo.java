package com.houtarouoreki.hullethell.helpers;

import com.badlogic.gdx.graphics.Color;

public class HealthBarsInfo {
    private final int healthPerBar = 10;
    private final int healthBarsPerLevel = 4;
    private int healthBarsAmount;
    private int level;
    private Color color;
    private float lastBarFill;

    public void update(float health) {
        level = getHealthBarsLevel(health);
        color = getColor(health);
        lastBarFill = getLastBarFill(health);
        healthBarsAmount = getHealthBarsAmount(health);
    }

    public int getHealthBarsAmount() {
        return healthBarsAmount;
    }

    public Color getColor() {
        return color;
    }

    public float getLastBarFill() {
        return lastBarFill;
    }

    private int getHealthBarsLevel(float health) {
        return (int)(health - 1) / (healthBarsPerLevel * healthPerBar);
    }

    private Color getColor(float health) {
        java.awt.Color temp = java.awt.Color.getHSBColor(level / 6.25f, 0.75f, 0.8f);
        return new Color(temp.getRed() / 255f, temp.getGreen() / 255f, temp.getBlue() / 255f, 1);
    }

    private float getLastBarFill(float health) {
        float remainder = health % healthPerBar;
        return remainder == 0 ? 1 : remainder / healthPerBar;
    }

    private float getLastLevelHealth(float health) {
        return health - level * healthPerBar * healthBarsPerLevel;
    }

    private int getHealthBarsAmount(float health) {
        return (int)(getLastLevelHealth(health) - 1) / healthPerBar;
    }
}
