package com.houtarouoreki.hullethell.numbers;

public class LoopIntTest {
    public LoopIntTest() {
        LoopInt tested = new LoopInt(0, 8 - 1);
        if (tested.decremented() != 7)
            throw new Error(tested.decremented() + " != " + 7);
    }
}
