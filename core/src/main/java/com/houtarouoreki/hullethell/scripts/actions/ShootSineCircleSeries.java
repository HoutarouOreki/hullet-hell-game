package com.houtarouoreki.hullethell.scripts.actions;

public class ShootSineCircleSeries extends ShootCircleSeriesAction {
    private float repeats = 1;

    @Override
    protected float getBulletRotation(int bulletNumber) {
        float direction = 360f / getAmount() * bulletNumber;
        float completion = (float) (getTimeSinceStarted() / getDuration());
        direction += getMaxRotation() * (float) Math.sin(Math.PI * 2 * completion * repeats);
        return direction;
    }

    @Override
    protected void addArgumentsInfo() {
        super.addArgumentsInfo();
        addRepeatsArg(this::setRepeats, true);
    }

    private void setRepeats(float repeats) {
        this.repeats = repeats;
    }
}
