package com.houtarouoreki.hullethell.graphics.flow;

import com.houtarouoreki.hullethell.graphics.Drawable;

public class FlowNode extends Drawable {
    public FlowNode(Drawable child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    public void add(Drawable child) {
        throw new RuntimeException("Can't add children to FlowNode");
    }

    public Drawable getChild() {
        return children.get(0);
    }
}
