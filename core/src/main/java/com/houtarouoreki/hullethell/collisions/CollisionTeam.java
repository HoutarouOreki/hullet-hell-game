package com.houtarouoreki.hullethell.collisions;

public enum CollisionTeam {
    ENEMY,
    ENEMY_BULLETS,
    PLAYER_SHIP,
    PLAYER_BULLETS,
    ENVIRONMENT,
    ITEMS;

    public CollisionTeam getBulletTeam() {
        if (this == PLAYER_SHIP)
            return PLAYER_BULLETS;
        else if (this == ENEMY)
            return ENEMY_BULLETS;
        else
            throw new IllegalArgumentException();
    }
}
