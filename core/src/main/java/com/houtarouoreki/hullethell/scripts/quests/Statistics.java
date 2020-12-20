package com.houtarouoreki.hullethell.scripts.quests;

import com.houtarouoreki.hullethell.entities.Item;

import java.util.HashMap;

public class Statistics {
    private final HashMap<String, Integer> collectedItems = new HashMap<String, Integer>();

    public void addItem(String itemName) {
        if (collectedItems.containsKey(itemName))
            collectedItems.put(itemName, collectedItems.get(itemName) + 1);
        else
            collectedItems.put(itemName, 1);
    }

    public int getItemAmount(String itemName) {
        if (collectedItems.containsKey(itemName))
            return collectedItems.get(itemName);
        return 0;
    }
}
