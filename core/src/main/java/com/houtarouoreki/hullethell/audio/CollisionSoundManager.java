package com.houtarouoreki.hullethell.audio;

import com.badlogic.gdx.math.Interpolation;
import com.houtarouoreki.hullethell.HulletHellGame;
import com.houtarouoreki.hullethell.collisions.CollisionManager;
import com.houtarouoreki.hullethell.environment.Updatable;

public class CollisionSoundManager implements Updatable {
    private final CollisionManager collisionManager;
    private float sinceLastCollision = 10;
    private long soundId;
    private boolean started;

    public CollisionSoundManager(CollisionManager collisionManager) {
        this.collisionManager = collisionManager;
    }

    public void start() {
        soundId = HulletHellGame.getSoundManager()
                .playSound("collision-loop", 0.1f);
        HulletHellGame.getSoundManager()
                .setLooping("collision-loop", soundId, true);
        started = true;
    }

    public void stop() {
        if (started)
            HulletHellGame.getSoundManager().stopSound("collision-loop", soundId);
    }

    @Override
    public void update(float delta) {
        if (!started)
            start();
        if (collisionManager.currentStepCollisions.size() > 0)
            sinceLastCollision = 0;
        float margin = 0.2f;

        // setting end parameter to 0 mutes main menu music for some reason
        float completion = sinceLastCollision / margin;
        float volume = sinceLastCollision <= margin ?
                Interpolation.sineIn.apply(0.1f, 0.001f, completion) :
                0;

        HulletHellGame.getSoundManager()
                .setVolume("collision-loop", soundId, volume);
        sinceLastCollision += delta;
    }
}
