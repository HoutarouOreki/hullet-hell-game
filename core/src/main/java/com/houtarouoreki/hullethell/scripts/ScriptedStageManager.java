package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;

import java.util.*;

public class ScriptedStageManager {
    private final HashMap<String, Integer> flags = new HashMap<>();
    private final LinkedList<ScriptedSection> waitingSections = new LinkedList<>();
    private final LinkedList<ScriptedSection> activeSections = new LinkedList<>();
    private int allBodiesAmount;
    private int bodiesRemovedPrevSections;

    public ScriptedStageManager(World world, StageConfiguration script, DialogueBox dialogueBox) {
        for (ScriptedSectionConfiguration sectionConfiguration : script.sections) {
            ScriptedSection section;
            if (sectionConfiguration.type.equals("dialogue"))
                section = new ScriptedDialogueSection(world, sectionConfiguration, dialogueBox);
            else if (sectionConfiguration.type.equals("while"))
                section = new ScriptedWhileSection(world, sectionConfiguration, dialogueBox);
            else
                section = new ScriptedSection(world, sectionConfiguration, dialogueBox);
            waitingSections.add(section);
            allBodiesAmount += section.getAllBodiesAmount();
        }
    }

    public void update(double delta) {
        startSections();
        updateSections(delta);
    }

    private boolean canStartSection(ScriptedSection section) {
        for (String flag : section.flagsRequiredToStart.keySet()) {
            if (getFlagValue(flag) < section.flagsRequiredToStart.get(flag))
                return false;
        }
        return true;
    }

    private void startSections() {
        Iterator<ScriptedSection> i = waitingSections.iterator();
        while (i.hasNext()) {
            ScriptedSection section = i.next();
            if (canStartSection(section)) {
                activeSections.add(section);
                i.remove();
            }
        }
    }

    private void updateSections(double delta) {
        Iterator<ScriptedSection> i = activeSections.iterator();
        while (i.hasNext()) {
            ScriptedSection activeSection = i.next();
            activeSection.update(delta);
            if (activeSection.isFinished()) {
                i.remove();
                incrementFlag("sectionDone:" + activeSection.name);
            }
        }
    }

    public int getFlagValue(String flag) {
        if (!flags.containsKey(flag))
            return 0;
        return flags.get(flag);
    }

    public void incrementFlag(String flag) {
        int oldValue = getFlagValue(flag);
        flags.put(flag, oldValue + 1);
    }

    public String getCurrentStageName() {
        return ""; // TODO
    }

    public int getWaitingBodiesCount() {
        return 0; // TODO
    }

    public int getActiveBodiesCount() {
        return 0; // TODO
    }

    public int getBodiesCount() {
        return 0; // TODO
    }

    public int getBodiesRemovedAmount() {
        return 0; // TODO
    }

    public int getSectionWaitingActions() {
        return 0; // TODO
    }

    public int getSectionCurrentActions() {
        return 0; // TODO
    }

    public float getProgression() {
        return 0; // TODO
    }

    public boolean isFinished() {
        return waitingSections.isEmpty() && activeSections.isEmpty();
    }
}
