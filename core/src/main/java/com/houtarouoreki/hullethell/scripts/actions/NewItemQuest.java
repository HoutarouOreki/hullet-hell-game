package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.quests.ItemQuest;

import java.util.HashMap;

public class NewItemQuest extends ScriptedAction {
    private String name;
    private final HashMap<String, Integer> items = new HashMap<String, Integer>();

    @Override
    protected void initialiseArguments() {
        super.initialiseArguments();
        name = arguments.get(0);
        String[] amountItems = arguments.get(1).split(" / ");
        for (String amountItem : amountItems) {
            String[] s = amountItem.split(" ");
            items.put(s[1], Integer.parseInt(s[0]));
        }
    }

    @Override
    protected void performAction() {
        world.questManager.registerQuest(new ItemQuest(name, world.statistics, items));
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }
}
