package com.houtarouoreki.hullethell.scripts.actions;

import com.houtarouoreki.hullethell.scripts.ScriptedAction;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionMatcherArg;
import com.houtarouoreki.hullethell.scripts.actions.interpreters.ActionStringArg;
import com.houtarouoreki.hullethell.scripts.quests.ItemQuest;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewItemQuest extends ScriptedAction {
    private String name;
    private final HashMap<String, Integer> items = new HashMap<>();
    private static final Pattern quest_id_pattern = Pattern.compile("id: (\\w+)");

    @Override
    protected void performAction() {
        world.questManager.registerQuest(new ItemQuest(name, world.statistics, items));
        setFinished();
    }

    @Override
    public int bodiesAmount() {
        return 0;
    }

    @Override
    protected void addArgumentsInfo() {
        parser.stringArgs.add(new ActionStringArg(
                "Quest name",
                "Used for flags. It can only contain letters, " +
                        "numbers and underscores.",
                "id: copperQuest",
                quest_id_pattern,
                this::setName,
                false
        ));
        parser.matcherArgs.add(new ActionMatcherArg(
                "Item",
                "How many of an item",
                "3 copperOre, 9 ironOre",
                item_amount_pattern,
                this::addItem,
                false
        ));
    }

    private void addItem(Matcher matcher) {
        items.put(matcher.group("itemName"),
                Integer.parseInt(matcher.group("itemAmount")));
    }

    private void setName(String s) {
        name = s;
    }
}
