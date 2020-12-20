package com.houtarouoreki.hullethell.scripts.quests;

import java.util.HashMap;
import java.util.Map;

public class ItemQuest extends Quest {
    private final HashMap<String, Integer> items;

    public ItemQuest(String name, Statistics statistics, Map<String, Integer> items) {
        super(name, statistics);
        this.items = new HashMap<String, Integer>(items);
    }

    @Override
    public boolean isDone() {
        for (String itemName : items.keySet()) {
            if (statistics.getItemAmount(itemName) < items.get(itemName))
                return false;
        }
        return true;
    }
}
