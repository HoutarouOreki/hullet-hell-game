package com.houtarouoreki.hullethell.scripts.quests;

import com.houtarouoreki.hullethell.environment.Finishable;

public abstract class Quest implements Finishable {
    public final String name;
    protected final Statistics statistics;

    public Quest(String name, Statistics statistics) {
        this.name = name;
        this.statistics = statistics;
    }

    public abstract boolean isDone();
}
