package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.math.Vector2;

public class PaddingMargin {
    public float top;
    public float bottom;
    public float left;
    public float right;

    public PaddingMargin() {}

    public PaddingMargin(float top, float bottom, float left, float right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public PaddingMargin(float horizontal, float vertical) {
        setHorizontal(horizontal);
        setVertical(vertical);
    }

    public float getTotalHorizontal() {
        return left + right;
    }

    public float getTotalVertical() {
        return top + bottom;
    }

    public void setHorizontal(float value) {
        left = right = value;
    }

    public void setVertical(float value) {
        top = bottom = value;
    }

    public Vector2 getTotal() {
        return new Vector2(getTotalHorizontal(), getTotalVertical());
    }

    public Vector2 getLeftTop() {
        return new Vector2(left, top);
    }

    public Vector2 getRightBottom() {
        return new Vector2(right, bottom);
    }

    public PaddingMargin cpy() {
        return new PaddingMargin(top, bottom, left, right);
    }

    public void set(PaddingMargin value) {
        top = value.top;
        bottom = value.bottom;
        left = value.left;
        right = value.right;
    }
}
