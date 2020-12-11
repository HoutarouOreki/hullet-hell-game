package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public abstract class Drawable {
    public final List<Drawable> children = new ArrayList<Drawable>();
    private final Vector2 position = new Vector2(0, 0);
    private final Vector2 size = new Vector2(1, 1);
    private final Vector2 padding = new Vector2(0, 0);
    private final Vector2 margin = new Vector2(0, 0);
    private final Vector2 anchor = new Vector2(0, 0);
    private final Vector2 origin = new Vector2(0, 0);
    private EnumSet<Axes> relativePositionAxes = EnumSet.noneOf(Axes.class);
    private EnumSet<Axes> relativeSizeAxes = EnumSet.noneOf(Axes.class);
    private EnumSet<Axes> relativePaddingAxes = EnumSet.noneOf(Axes.class);
    private Drawable parent;
    private Color color = Color.WHITE;
    private boolean visibility = true;

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public Vector2 getSize() {
        return size.cpy();
    }

    public void setSize(Vector2 size) {
        this.size.set(size);
    }

    public Vector2 getContentRenderSize() {
        Vector2 contentSize = getRenderSize().cpy();
        if (relativePaddingAxes.contains(Axes.HORIZONTAL))
            contentSize.x -= 2 * padding.x * getSize().x;
        else
            contentSize.x -= 2 * padding.x;
        if (relativePaddingAxes.contains(Axes.VERTICAL))
            contentSize.y -= 2 * padding.y * getSize().y;
        else
            contentSize.y -= 2 * padding.y;
        return contentSize;
    }

    public Vector2 getRenderSize() {
        Vector2 renderSize = getSize().cpy();
        if (relativeSizeAxes.contains(Axes.HORIZONTAL))
            renderSize.x *= parent.getContentRenderSize().x;
        if (relativeSizeAxes.contains(Axes.VERTICAL))
            renderSize.y *= parent.getContentRenderSize().y;
        renderSize.sub(getMargin().scl(2));
        return renderSize;
    }

    public Vector2 getRenderPosition() {
        Vector2 renderPos = getPosition();
        if (relativeSizeAxes.contains(Axes.HORIZONTAL))
            renderPos.x *= parent.getContentRenderSize().x;
        if (relativeSizeAxes.contains(Axes.VERTICAL))
            renderPos.y *= parent.getContentRenderSize().y;
        renderPos.add(getMargin());
        if (parent == null)
            return renderPos;
        return renderPos.add(parent.getRenderPosition())
                .add(parent.getPadding())
                .add(parent.getContentRenderSize().scl(getAnchor()))
                .sub(getRenderSize().scl(getOrigin()));
    }

    public final void update(float delta) {
        for (Drawable child : children)
            child.update(delta);
        onUpdate(delta);
    }

    protected void onUpdate(float delta) {
    }

    public final void render(Graphics g) {
        if (!visibility)
            return;
        g.setColor(color);
        draw(g);
        for (Drawable child : children)
            child.render(g);
    }

    public void draw(Graphics g) {
    }

    public EnumSet<Axes> getRelativePositionAxes() {
        return relativePositionAxes;
    }

    public void setRelativePositionAxes(EnumSet<Axes> relativePositionAxes) {
        this.relativePositionAxes = relativePositionAxes;
    }

    public EnumSet<Axes> getRelativeSizeAxes() {
        return relativeSizeAxes;
    }

    public void setRelativeSizeAxes(EnumSet<Axes> relativeSizeAxes) {
        this.relativeSizeAxes = relativeSizeAxes;
    }

    public Drawable getParent() {
        return parent;
    }

    public void add(Drawable child) {
        child.parent = this;
        children.add(child);
    }

    public void addAll(Collection<? extends Drawable> asList) {
        children.addAll(asList);
    }

    public void remove(Drawable child) {
        child.parent = null;
        children.remove(child);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector2 getPadding() {
        return padding.cpy();
    }

    public void setPadding(Vector2 padding) {
        this.padding.set(padding);
    }

    public Vector2 getMargin() {
        return margin.cpy();
    }

    public void setMargin(Vector2 margin) {
        this.margin.set(margin);
    }

    public EnumSet<Axes> getRelativePaddingAxes() {
        return relativePaddingAxes;
    }

    public void setRelativePaddingAxes(EnumSet<Axes> relativePaddingAxes) {
        this.relativePaddingAxes = relativePaddingAxes;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Vector2 getAnchor() {
        return anchor.cpy();
    }

    public void setAnchor(Vector2 anchor) {
        this.anchor.set(anchor);
    }

    public Vector2 getOrigin() {
        return origin.cpy();
    }

    public void setOrigin(Vector2 origin) {
        this.origin.set(anchor);
    }
}
