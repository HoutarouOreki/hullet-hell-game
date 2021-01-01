package com.houtarouoreki.hullethell.scripts.actions;

public class ShootCircleSeriesAction extends ShootCircleAction {
    private float duration;
    private float intervalDuration;
    private int shotsShot;
    private float maxRotation;

    protected float getMaxRotation() {
        return maxRotation;
    }

    protected void setMaxRotation(float maxRotation) {
        this.maxRotation = maxRotation;
    }

    protected float getIntervalDuration() {
        return intervalDuration;
    }

    protected void setIntervalDuration(float intervalDuration) {
        this.intervalDuration = intervalDuration;
    }

    protected boolean isTimeForNextShot() {
        return shotsShot * intervalDuration <= getTimeSinceStarted();
    }

    @Override
    protected void performAction() {
        if (isTimeForNextShot()) {
            super.performAction();
            shotsShot++;
        }
    }

    @Override
    protected float getBulletRotation(int bulletNumber) {
        float rotationPerInterval = maxRotation * intervalDuration / duration;
        return super.getBulletRotation(bulletNumber) + rotationPerInterval * shotsShot;
    }

    @Override
    protected void addArgumentsInfo() {
        super.addArgumentsInfo();
        addDurationArg(this::setDuration, false);
        addIntervalArg(this::setIntervalDuration, false);
        addMaxRotationArg(this::setMaxRotation, true);
    }

    @Override
    public boolean isFinished() {
        return getTimeSinceStarted() > duration;
    }

    protected float getDuration() {
        return duration;
    }

    protected void setDuration(float duration) {
        this.duration = duration;
    }

    protected int getShotsShot() {
        return shotsShot;
    }
}
