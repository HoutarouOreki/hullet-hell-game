package com.houtarouoreki.hullethell.graphics;

import com.badlogic.gdx.graphics.Color;
import com.houtarouoreki.hullethell.numbers.Vector2;
import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public abstract class Drawable {
    public final List<Drawable> children = new ArrayList<>();
    public float widthHeightRatioForFitFill = 1;
    private FillMode fillMode = FillMode.STRETCH;
    private final PaddingMargin padding = new PaddingMargin();
    private final PaddingMargin margin = new PaddingMargin();
    private Vector2 position = new Vector2();
    private Vector2 size = new Vector2(1, 1);
    private Vector2 anchor = new Vector2();
    private Vector2 origin = new Vector2();
    private float time = 0;
    private EnumSet<Axes> relativePositionAxes = EnumSet.noneOf(Axes.class);
    private EnumSet<Axes> relativeSizeAxes = EnumSet.noneOf(Axes.class);
    private EnumSet<Axes> relativePaddingAxes = EnumSet.noneOf(Axes.class);
    private EnumSet<Axes> autoSizeAxes = EnumSet.noneOf(Axes.class);
    private Drawable parent;
    private Color color = Color.WHITE;
    private boolean visibility = true;

    public void setParent(Drawable drawable) {
        parent = drawable;
    }

    public void setX(float x) {
        this.setPosition(this.getPosition().withX(x));
    }

    public void setY(float y) {
        this.setPosition(this.getPosition().withY(y));
    }

    public Vector2 getContentRenderSize() {
        Vector2 contentSize = getRenderSize();
        float xAddition = 0;
        float yAddition = 0;
        if (relativePaddingAxes.contains(Axes.HORIZONTAL))
            xAddition -= padding.getTotalHorizontal() * getRenderSize().x;
        else
            xAddition -= padding.getTotalHorizontal();
        if (relativePaddingAxes.contains(Axes.VERTICAL))
            yAddition -= padding.getTotalVertical() * getRenderSize().y;
        else
            yAddition -= padding.getTotalVertical();
        return contentSize.add(new Vector2(xAddition, yAddition));
    }

    public Vector2 getRenderSize() {
        Vector2 renderSize = getSize();
        if (getFillMode() == FillMode.STRETCH) {
            if (relativeSizeAxes.contains(Axes.HORIZONTAL))
                renderSize = renderSize.sclX(parent.getContentRenderSize().x);
            if (relativeSizeAxes.contains(Axes.VERTICAL))
                renderSize = renderSize.sclY(parent.getContentRenderSize().y);
            renderSize = renderSize.sub(getMargin().getTotal());
        } else if (getFillMode() == FillMode.FILL) {
            renderSize = renderSize.withX(renderSize.y * widthHeightRatioForFitFill);
            renderSize = renderSize.fill(parent.getContentRenderSize().scl(size));
            renderSize = renderSize.scl(getSize());
        } else if (getFillMode() == FillMode.FIT) {
            renderSize = renderSize.withX(renderSize.y * widthHeightRatioForFitFill);
            renderSize = renderSize.fit(parent.getContentRenderSize().scl(size));
            renderSize = renderSize.scl(getSize());
        }
        if (!children.isEmpty()) {
            if (autoSizeAxes.contains(Axes.VERTICAL)) {
                float maxY = 0;
                for (Drawable child : children) {
                    if (child.relativeSizeAxes.contains(Axes.VERTICAL))
                        continue;
                    maxY = Math.max(maxY, child.getPosition().y + child.getSize().y);
                }
                renderSize = renderSize.withY(maxY);
            }
            if (autoSizeAxes.contains(Axes.HORIZONTAL)) {
                float maxX = 0;
                for (Drawable child : children) {
                    if (child.relativeSizeAxes.contains(Axes.HORIZONTAL))
                        continue;
                    maxX = Math.max(maxX, child.getPosition().x + child.getSize().x);
                }
                renderSize = renderSize.withX(maxX);
            }
        }
        return renderSize;
    }

    public Vector2 getRenderPosition() {
        Vector2 renderPos = getPosition();
        if (relativePositionAxes.contains(Axes.HORIZONTAL))
            renderPos = renderPos.sclX(parent.getContentRenderSize().x);
        if (relativePositionAxes.contains(Axes.VERTICAL))
            renderPos = renderPos.sclY(parent.getContentRenderSize().y);
        renderPos = renderPos.add(getMargin().getLeftTop());
        if (parent == null)
            return renderPos;
        return renderPos.add(parent.getRenderPosition())
                .add(parent.getPadding().getLeftTop())
                .add(parent.getContentRenderSize().scl(getAnchor()))
                .sub(getRenderSize().scl(getOrigin()));
    }

    public final void update(float delta) {
        for (Drawable child : children)
            child.update(delta);
        onUpdate(delta);
        time += delta;
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

    public void addAll(Collection<? extends Drawable> collection) {
        for (Drawable child : collection) {
            add(child);
        }
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

    public PaddingMargin getPadding() {
        return padding.cpy();
    }

    public void setPadding(PaddingMargin padding) {
        this.padding.set(padding);
    }

    public PaddingMargin getMargin() {
        return margin.cpy();
    }

    public void setMargin(PaddingMargin margin) {
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

    public float getTime() {
        return time;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Vector2 getAnchor() {
        return anchor;
    }

    public void setAnchor(Vector2 anchor) {
        this.anchor = anchor;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    public FillMode getFillMode() {
        return fillMode;
    }

    public void setFillMode(FillMode fillMode) {
        this.fillMode = fillMode;
    }

    public EnumSet<Axes> getAutoSizeAxes() {
        return autoSizeAxes;
    }

    public void setAutoSizeAxes(EnumSet<Axes> autoSizeAxes) {
        this.autoSizeAxes = autoSizeAxes;
    }
}
