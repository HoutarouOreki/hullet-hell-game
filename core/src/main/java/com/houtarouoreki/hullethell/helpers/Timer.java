package com.houtarouoreki.hullethell.helpers;

public class Timer {
    public float TimeLeft = 0;
    private boolean restartOnFinish;
    private float interval;

    public Timer(float time) {
        TimeLeft = time;
    }

    public Timer() {
    }

    public boolean update(float delta) {
        if (TimeLeft > 0) {
            TimeLeft -= delta;

            if (TimeLeft <= 0) {
                TimeLeft = restartOnFinish ? interval : 0;
                return true;
            }
        }
        return false;
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
        if (interval > 0) {
            restartOnFinish = true;
        }
    }
}
