package com.houtarouoreki.hullethell.numbers;

import java.util.ArrayList;
import java.util.List;

public class LimitedNumber<T extends Comparable<T>> {
    private final List<ValueChangeListener<T>> listeners;
    private T min;
    private T max;
    private T value;

    public LimitedNumber(T value, T min, T max) {
        listeners = new ArrayList<ValueChangeListener<T>>();
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

    public void setValue(T value) {
        T oldValue = getValue();
        if (value.compareTo(max) > 0)
            this.value = max;
        else if (value.compareTo(min) < 0)
            this.value = min;
        else
            this.value = value;

        for (ValueChangeListener<T> listener : listeners)
            listener.onValueChanged(oldValue, value);
    }

    private void validateMinMax() {
        if (max.compareTo(min) < 0)
            throw new Error("max (" + max + ") cannot be lower than min (" + min + ")");
    }

    public final LimitedNumber<T> cpyLimitedNumber() {
        return new LimitedNumber<T>(value, min, max);
    }

    public void addListener(ValueChangeListener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(ValueChangeListener<T> listener) {
        listeners.remove(listener);
    }

    public void clearListeners() {
        listeners.clear();
    }

    public interface ValueChangeListener<T> {
        void onValueChanged(T oldValue, T newValue);
    }
}
