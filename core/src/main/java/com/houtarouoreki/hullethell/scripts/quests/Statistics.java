package com.houtarouoreki.hullethell.scripts.quests;

import java.util.HashMap;

public class Statistics {
    private final HashMap<String, Integer> collectedItems = new HashMap<>();

    public void addItem(String itemName) {
        int itemAmount = getItemAmount(itemName);
        collectedItems.put(itemName, itemAmount + 1);
    }

    public int getItemAmount(String itemName) {
        if (collectedItems.containsKey(itemName))
            return collectedItems.get(itemName);
        return 0;
    }
}
