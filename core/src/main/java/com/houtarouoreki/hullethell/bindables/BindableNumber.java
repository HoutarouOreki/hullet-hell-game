package com.houtarouoreki.hullethell.bindables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BindableNumber<T extends Comparable<T>> {
    private final List<ValueChangeListener<T>> listeners;
    private final HashSet<BindableNumber<T>> binds;
    private T min;
    private T max;
    private T value;

    public BindableNumber(T value, T min, T max) {
        listeners = new ArrayList<>();
        binds = new HashSet<>();
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
        if (min.equals(this.min))
            return;
        this.min = min;
        setValue(value);
        for (BindableNumber<T> bind : binds)
            bind.setMin(min);
    }

    public T getMax() {
        if (max.compareTo(min) < 0)
            throw new Error("max (" + max + ") cannot be lower than min (" + min + ")");
        return max;
    }

    public void setMax(T max) {
        if (max.equals(this.max))
            return;
        this.max = max;
        setValue(value);
        for (BindableNumber<T> bind : binds)
            bind.setMax(max);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        T oldValue = getValue();
        if (value.equals(oldValue))
            return;
        if (value.compareTo(max) > 0)
            this.value = max;
        else if (value.compareTo(min) < 0)
            this.value = min;
        else
            this.value = value;

        for (ValueChangeListener<T> listener : listeners)
            listener.onValueChanged(oldValue, value);
        for (BindableNumber<T> bind : binds)
            bind.setValue(this.value);
    }

    public void setMaxValue() {
        setValue(getMax());
    }

    public void setMinValue() {
        setValue(getMin());
    }

    private void validateMinMax() {
        if (max.compareTo(min) < 0)
            throw new Error("max (" + max + ") cannot be lower than min (" + min + ")");
    }

    public final BindableNumber<T> cpyLimitedNumber() {
        return new BindableNumber<>(value, min, max);
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

    /**
     * Mimics limits and value from the target and propagates
     * any subsequent changes to value, min and max both ways.
     */
    public void bindTo(BindableNumber<T> target) {
        setMin(target.getMin());
        setMax(target.getMax());
        setValue(target.getValue());
        binds.add(target);
        target.binds.add(this);
    }
}
