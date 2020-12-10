package com.houtarouoreki.hullethell.numbers;

public class LimitedNumber<T extends Comparable<T>> {
    private T min;
    private T max;
    private T value;

    public LimitedNumber(T value, T min, T max) {
        this.min = min;
        this.max = max;
        setValue(value);
    }

    public T getMin() {
        if (min.compareTo(max) > 0)
            throw new Error("min (" + min + ") cannot be higher than max (" + max + ")");
        return min;
    }

    public void setMin(T min) {
        this.min = min;
        setValue(value);
    }

    public T getMax() {
        if (max.compareTo(min) < 0)
            throw new Error("max (" + max + ") cannot be lower than min (" + min + ")");
        return max;
    }

    public void setMax(T max) {
        this.max = max;
        setValue(value);
    }

    public T getValue() {
        validateMinMax();
        return value;
    }

    private void validateMinMax() {
        if (max.compareTo(min) < 0)
            throw new Error("max (" + max + ") cannot be lower than min (" + min + ")");
    }

    public void setValue(T value) {
        if (value.compareTo(max) > 0)
            this.value = max;
        else if (value.compareTo(min) < 0)
            this.value = min;
        else
            this.value = value;
    }

    public final LimitedNumber<T> cpyLimitedNumber() {
        return new LimitedNumber<T>(value, min, max);
    }
}
