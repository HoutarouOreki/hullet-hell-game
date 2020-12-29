package com.houtarouoreki.hullethell.numbers;

import com.badlogic.gdx.math.Interpolation;

public class Vector2 {
    public static final Vector2 ZERO = new Vector2();
    public final float x;
    public final float y;

    public Vector2() {
        this.x = this.y = 0;
    }

    public Vector2(float xy) {
        this.x = this.y = xy;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 interpolate(Vector2 start, Vector2 end, float a,
                                      Interpolation interpolation) {
        return new Vector2(interpolation.apply(start.x, end.x, a),
                interpolation.apply(start.y, end.y, a));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 sub(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 sclX(float x) {
        return new Vector2(this.x * x, y);
    }

    public Vector2 sclY(float y) {
        return new Vector2(x, this.y * y);
    }

    public Vector2 scl(Vector2 other) {
        return new Vector2(x * other.x, y * other.y);
    }

    public Vector2 div(float divider) {
        if (divider == 0)
            throw new ArithmeticException("Divider is equal to 0");
        return new Vector2(x / divider, y / divider);
    }

    public Vector2 div(Vector2 other) {
        if (other.x == 0)
            throw new ArithmeticException("The other vector has x equal to 0");
        if (other.y == 0)
            throw new ArithmeticException("The other vector has y equal to 0");
        return new Vector2(x / other.x, y / other.y);
    }

    public Vector2 divideNumber(float dividend) {
        return new Vector2(dividend / x, dividend / y);
    }

    /**
     * @return A vector with the same direction
     * with a length of 1, or 0 if the vector's
     * length was 0.
     */
    public Vector2 nor() {
        float length = len();
        if (length == 0)
            return new Vector2();
        return new Vector2(x / length, y / length);
    }

    public float len() {
        return (float) Math.sqrt(len2());
    }

    public float len2() {
        return x * x + y * y;
    }

    public float dot(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public float dst(Vector2 other) {
        return (float) Math.sqrt(dst2(other));
    }

    public float dst2(Vector2 other) {
        float dx = other.x - x;
        float dy = other.y - y;
        return dx * dx + dy * dy;
    }

    /**
     * x = 0 and y = -1 returns 0 degrees.
     * x = 1 and y = 0 returns 90 degrees.
     *
     * @return The rotation of this vector
     * compared to a (0, -1) vector, increasing clockwise.
     * Values from 0 to 360.
     */
    public float angle() {
        float angle = (float) Math.toDegrees(Math.atan2(x, -y));
        if (angle < 0)
            angle += 360;
        return angle;
    }

    /**
     * @return A vector rotated clockwise.
     */
    public Vector2 rotated(float degrees, boolean yUp) {
        if (yUp)
            degrees *= -1;
        double radians = Math.toRadians(degrees);

        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        return new Vector2((float) (x * cos - y * sin), (float) (x * sin + y * cos));
    }

    public Vector2 rotated90(int times) {
        Vector2 v = new Vector2(x, y);
        times %= 4;

        for (int i = 0; i < times; i++)
            v = v.rotated90();
        return v;
    }

    public Vector2 rotated90() {
        double cos = 0;
        double sin = 1;

        return new Vector2((float) (x * cos - y * sin), (float) (x * sin + y * cos));
    }

    public Vector2 clamped(Vector2 min, Vector2 max) {
        float x = this.x;
        float y = this.y;
        x = Math.max(min.x, x);
        y = Math.max(min.y, y);
        x = Math.min(max.x, x);
        y = Math.min(max.y, y);

        return new Vector2(x, y);
    }

    public Vector2 fit(Vector2 max) {
        float scaleDownX = max.x / x;
        float scaleDownY = max.y / y;
        return scl(Math.min(scaleDownX, scaleDownY));
    }

    public Vector2 scl(float scale) {
        return new Vector2(x * scale, y * scale);
    }

    public Vector2 fill(Vector2 min) {
        float scaleUpX = min.x / x;
        float scaleUpY = min.y / y;
        return scl(Math.max(scaleUpX, scaleUpY));
    }

    public boolean outsideOf(Vector2 topLeft, Vector2 bottomRight) {
        return lesserThan(topLeft) || greaterThan(bottomRight);
    }

    public boolean lesserThan(Vector2 other) {
        return x < other.x || y < other.y;
    }

    public boolean greaterThan(Vector2 other) {
        return x > other.x || y > other.y;
    }

    public Vector2 inv() {
        return new Vector2(1 / x, 1 / y);
    }

    public Vector2 withX(float x) {
        return new Vector2(x, y);
    }

    public Vector2 withY(float y) {
        return new Vector2(x, y);
    }

    public float getWidthHeightRatio() {
        return x / y;
    }
}
