package com.houtarouoreki.hullethell.graphics.flow;

import com.houtarouoreki.hullethell.graphics.Axes;
import com.houtarouoreki.hullethell.graphics.Drawable;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public class FlowContainer extends Drawable {
    public Axes direction;
    public float spacing;
    float lastComputedChildrenSize = -1;
    protected List<FlowNode> flowNodes = new ArrayList<>();

    public FlowContainer(Axes direction) {
        this.direction = direction;
    }

    @Override
    public void add(Drawable child) {
        throw new RuntimeException("Don't use add for flow container.");
    }

    public void addNonFlowItem(Drawable child) {
        super.add(child);
    }

    public void addFlowItem(Drawable child) {
        FlowNode flowNode = new FlowNode(child);
        super.add(flowNode);
        flowNodes.add(flowNode);
        flowNode.setParent(this);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        float maxPosition = 0;
        for (FlowNode node : flowNodes) {
            Vector2 nodeChildRenderSize = node.getChild().getRenderSize();
            if (direction == Axes.HORIZONTAL) {
                node.setX(maxPosition);
                maxPosition += nodeChildRenderSize.x;
            }
            else {
                node.setY(maxPosition);
                maxPosition += nodeChildRenderSize.y;
            }
            maxPosition += spacing;
        }
        if (!children.isEmpty())
            maxPosition -= spacing;
        lastComputedChildrenSize = maxPosition;
    }
}
