package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.configurations.StageConfiguration;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedSectionInitializationException;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedSectionUpdateException;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedStageInitializationException;
import com.houtarouoreki.hullethell.scripts.exceptions.ScriptedStageUpdateException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ScriptedStageManager {
    public final String name;
    private final HashMap<String, Integer> flags = new HashMap<>();
    private final LinkedList<ScriptedSection> waitingSections = new LinkedList<>();
    private final LinkedList<ScriptedSection> activeSections = new LinkedList<>();
    private int allBodiesAmount;
    private int bodiesRemovedPrevSections;

    public ScriptedStageManager(World world, StageConfiguration script, DialogueBox dialogueBox) throws ScriptedStageInitializationException {
        name = script.name;
        for (ScriptedSectionConfiguration sectionConfiguration : script.sections) {
            ScriptedSection section;
            try {
                section = createScriptedSection(world, dialogueBox, sectionConfiguration);
            } catch (ScriptedSectionInitializationException e) {
                throw new ScriptedStageInitializationException(script, e);
            }
            waitingSections.add(section);
            allBodiesAmount += section.getAllBodiesAmount();
        }
    }

    private ScriptedSection createScriptedSection(World world, DialogueBox dialogueBox, ScriptedSectionConfiguration sectionConfiguration) throws ScriptedSectionInitializationException {
        switch (sectionConfiguration.type) {
            case "dialogue":
                return new ScriptedDialogueSection(world, sectionConfiguration, dialogueBox);
            case "recurring":
                return new ScriptedRecurringSection(world, sectionConfiguration, dialogueBox);
            default:
                return new ScriptedSection(world, sectionConfiguration, dialogueBox);
        }
    }

    @Override
    public String toString() {
        return "ScriptedStageManager:\n" +
                " waiting sections (" + waitingSections.size() + "): " +
                waitingSections.stream().map(scriptedSection -> scriptedSection.name).collect(Collectors.joining(", ")) + '\n' +
                " active sections: (" + activeSections.size() + "):\n" + activeSections.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public void update(double delta) throws ScriptedStageUpdateException {
        startSections();
        updateSections(delta);
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

    private void updateSections(double delta) throws ScriptedStageUpdateException {
        Iterator<ScriptedSection> i = activeSections.iterator();
        while (i.hasNext()) {
            ScriptedSection activeSection = i.next();
            try {
                activeSection.update(delta);
            } catch (ScriptedSectionUpdateException e) {
                throw new ScriptedStageUpdateException(this, e);
            }
            if (activeSection.isFinished()) {
                i.remove();
                incrementFlag("sectionDone:" + activeSection.name);
            }
        }
    }

    private boolean canStartSection(ScriptedSection section) {
        for (String flag : section.flagsRequiredToStart.keySet()) {
            if (getFlagValue(flag) < section.flagsRequiredToStart.get(flag))
                return false;
        }
        return true;
    }

    public void incrementFlag(String flag) {
        int oldValue = getFlagValue(flag);
        flags.put(flag, oldValue + 1);
    }

    public int getFlagValue(String flag) {
        if (!flags.containsKey(flag))
            return 0;
        return flags.get(flag);
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
