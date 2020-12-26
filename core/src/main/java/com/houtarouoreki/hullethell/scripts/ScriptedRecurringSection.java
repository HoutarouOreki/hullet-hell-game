package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.bindables.BindableNumber;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptedRecurringSection extends ScriptedSection{
    private final String flagToRestart;
    private final LinkedList<ScriptedSection> sections = new LinkedList<>();
    private final BindableNumber<Float> iterationTime;
    private final ScriptedSectionConfiguration conf;
    private final DialogueBox dialogueBox;

    public ScriptedRecurringSection(World world,
                                    ScriptedSectionConfiguration conf,
                                    DialogueBox dialogueBox) {
        super(world, conf, dialogueBox);
        this.conf = conf;
        this.dialogueBox = dialogueBox;
        Pattern parametersPattern = Pattern
                .compile("(?:every (?<period>\\d+(?:\\.\\d+)?) seconds)? ?(?:until (?<flag>.*))?");
        Matcher parametersMatch = parametersPattern.matcher(conf.parameters);
        if (!parametersMatch.matches())
            throw new IllegalArgumentException("Parameters in a wrong format");
        float period = Float.parseFloat(parametersMatch.group("period"));
        iterationTime = new BindableNumber<>(0f, 0f, period);
        flagToRestart = parametersMatch.group("flag");
        addNewSubsection();
    }

    @Override
    public void update(double delta) {
        Iterator<ScriptedSection> i = sections.listIterator();
        while (i.hasNext()) {
            ScriptedSection section = i.next();
            section.update(delta);
            if (section.isFinished())
                i.remove();
        }

        iterationTime.setValue((float) (iterationTime.getValue() + delta));

        if (iterationTime.isMax() && world.scriptedStageManager.getFlagValue(flagToRestart) == 0) {
            iterationTime.setMinValue();
            addNewSubsection();
        }
    }

    private void addNewSubsection() {
        sections.add(new ScriptedSection(world, conf, dialogueBox));
    }

    @Override
    public boolean isFinished() {
        return sections.isEmpty();
    }
}
