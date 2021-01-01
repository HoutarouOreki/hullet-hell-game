package com.houtarouoreki.hullethell.scripts.actions;

public class ShootMultipleSeriesAction extends ShootMultipleAction {
    private float intervalDuration;
    private float duration;
    private int shotsShot;

    @Override
    protected void performAction() {
        if (isTimeForNextShot()) {
            super.performAction();
            shotsShot++;
        }
    }

    protected boolean isTimeForNextShot() {
        return shotsShot * intervalDuration <= getTimeSinceStarted();
    }

    @Override
    public boolean isFinished() {
        return getTimeSinceStarted() / getDuration() > 1;
    }

    @Override
    protected void addArgumentsInfo() {
        super.addArgumentsInfo();
        addIntervalArg(this::setIntervalDuration, false);
        addDurationArg(this::setDuration, false);
    }

    protected float getDuration() {
        return duration;
    }

    protected void setDuration(float duration) {
        this.duration = duration;
    }

    protected float getIntervalDuration() {
        return intervalDuration;
    }

    protected void setIntervalDuration(float intervalDuration) {
        this.intervalDuration = intervalDuration;
    }
}
