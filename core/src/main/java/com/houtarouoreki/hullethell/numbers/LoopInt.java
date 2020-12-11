package com.houtarouoreki.hullethell.numbers;

import com.houtarouoreki.hullethell.bindables.BindableNumber;

public class LoopInt extends BindableNumber<Integer> {
    public LoopInt(Integer min, Integer max) {
        super(min, min, max);
    }

    public LoopInt(Integer value, Integer min, Integer max) {
        super(value, min, max);
    }

    public void increment() {
        setValue(getValue() + 1);
    }

    public int incremented() {
        return getValue(getValue() + 1);
    }

    public void decrement() {
        setValue(getValue() - 1);
    }

    public int decremented() {
        return getValue(getValue() - 1);
    }

    public int getRange() {
        return getMax() - getMin() + 1;
    }

    public int getValue(int value) {
        LoopInt temp = cpyLoopingInteger();
        temp.setValue(value);
        return temp.getValue();
    }

    @Override
    public void setValue(Integer value) {
        if (value < getMin() || value > getMax()) {
            value = (value % getRange()) + getMin();
            if (value < getMin())
                value += getRange();
            if (value > getMax())
                value -= getRange();
        }
        super.setValue(value);
    }

    public final LoopInt cpyLoopingInteger() {
        return new LoopInt(getValue(), getMin(), getMax());
    }
}
