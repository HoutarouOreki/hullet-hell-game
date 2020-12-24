package com.houtarouoreki.hullethell.entities;

import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionTeam;
import com.houtarouoreki.hullethell.configurations.ExplosiveConfiguration;

public class Explosive extends Entity {
    public final float explosionRadius;
    public final float explosionDamage;
    public final float explosionDuration;

    public Explosive(String configurationName) {
        String path = "explosives/" + configurationName;
        ExplosiveConfiguration c = HulletHellGame.getAssetManager().get(path + ".cfg");
        configuration = c;
        setHealth(c.maxHealth);
        setSize(c.size);
        setCollisionBody(c.collisionCircles);
        setShouldDespawnOOBounds(true);
        explosionRadius = c.explosionRadius;
        explosionDamage = c.explosionDamage;
        explosionDuration = c.explosionDuration;
    }

    public Explosion getExplosion() {
        Explosion explosion = new Explosion(explosionDamage, explosionRadius, explosionDuration);
        explosion.setPosition(getPosition());
        explosion.setTeam(CollisionTeam.ENVIRONMENT);
        return explosion;
    }
}
