package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.entities.Entity;
import com.houtarouoreki.hullethell.scripts.actions.MoveToAction;

import java.util.Arrays;
import java.util.List;

public abstract class ScriptedAction {
    public String type;
    public List<String> arguments;
    public Entity entity;
    private double scriptedTime;
    private double totalTime;
    private int ticks;
    private boolean finished;

    public static ScriptedAction createScriptedAction(String line) {
        String[] s = line.split(" // ")[0].split("\t+");
        String type = s[1].replace(":", "");
        ScriptedAction a;
        if (type.equals("position")) {
            a = new MoveToAction();
        } else {
            return null;
        }
        a.scriptedTime = Double.parseDouble(s[0]);
        a.arguments = Arrays.asList(s[2].split(", "));
        return a;
    }

    public double getScriptedTime() {
        return scriptedTime;
    }

    protected abstract void performAction();

    protected abstract void initialize();

    public void physics() {
        ticks++;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public int getTicks() {
        return ticks;
    }

    public boolean isFinished() {
        return finished;
    }

    protected void setFinished() {
        this.finished = true;
    }
}
