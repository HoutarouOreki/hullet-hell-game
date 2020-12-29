package com.houtarouoreki.hullethell.graphics.flow;

import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Rectangle;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowContainerTest {
    @Test
    void test1() {
        FlowContainer flowContainer = new FlowContainer(Axes.VERTICAL);

        Rectangle rect20 = new Rectangle();
        rect20.setSize(new Vector2(20));
        flowContainer.addFlowItem(rect20);

        Rectangle rect50 = new Rectangle();
        rect50.setSize(new Vector2(50));
        flowContainer.addFlowItem(rect50);

        flowContainer.update(0);

        assertEquals(70, flowContainer.lastComputedChildrenSize);
    }
}