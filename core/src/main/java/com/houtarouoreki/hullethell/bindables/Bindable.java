package com.houtarouoreki.hullethell.bindables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class Bindable<T> {
    private final List<ValueChangeListener<T>> listeners;
    private final HashSet<Bindable<T>> binds;
    private T value;

    public Bindable(T value) {
        listeners = new ArrayList<ValueChangeListener<T>>();
        binds = new HashSet<Bindable<T>>();
        setValue(value);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        T oldValue = getValue();
        if (oldValue.equals(value))
            return;
        else
            this.value = value;

        for (ValueChangeListener<T> listener : listeners)
            listener.onValueChanged(oldValue, value);
        for (Bindable<T> bind : binds)
            bind.setValue(value);
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
    public void bindTo(Bindable<T> target) {
        setValue(target.getValue());
        binds.add(target);
        target.binds.add(this);
    }
}
