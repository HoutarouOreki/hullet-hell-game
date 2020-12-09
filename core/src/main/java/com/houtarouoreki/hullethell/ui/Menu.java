package com.houtarouoreki.hullethell.ui;


import org.mini2Dx.core.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    public final List<MenuComponent> components;

    public Menu() {
        components = new ArrayList<MenuComponent>();
    }

    public void update(float delta) {
        for (MenuComponent component : components) {
            component.update(delta);
        }
    }

    public void render(Graphics g) {
        for (MenuComponent component : components) {
            component.render(g);
        }
    }
}
