package com.houtarouoreki.hullethell.scripts;

import com.houtarouoreki.hullethell.bindables.BindableNumber;
import com.houtarouoreki.hullethell.configurations.ScriptedBodyConfiguration;
import com.houtarouoreki.hullethell.configurations.ScriptedSectionConfiguration;
import com.houtarouoreki.hullethell.environment.World;
import com.houtarouoreki.hullethell.graphics.dialogue.DialogueBox;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScriptedRecurringSection extends ScriptedSection{
    private final String flagToRestart;
    private final LinkedList<ScriptedSection> sections = new LinkedList<>();
    private final BindableNumber<Float> iterationTime;
    private final ScriptedSectionConfiguration repeatedConf;
    private final DialogueBox dialogueBox;

    public ScriptedRecurringSection(World world,
                                    ScriptedSectionConfiguration conf,
                                    DialogueBox dialogueBox) {
        super(world, conf, dialogueBox);
        this.dialogueBox = dialogueBox;
        Pattern parametersPattern = Pattern
                .compile("(?:every (?<period>\\d+(?:\\.\\d+)?) seconds)? ?(?:until (?<flag>.*))?");
        Matcher parametersMatch = parametersPattern.matcher(conf.parameters);
        if (!parametersMatch.matches())
            throw new IllegalArgumentException("Parameters in a wrong format");
        float period = Float.parseFloat(parametersMatch.group("period"));
        iterationTime = new BindableNumber<>(0f, 0f, period);
        flagToRestart = parametersMatch.group("flag");
        sections.add(new ScriptedSection(world, conf, dialogueBox));
        repeatedConf = getRepeatedSectionConfiguration(conf);
    }

    private ScriptedSectionConfiguration getRepeatedSectionConfiguration(ScriptedSectionConfiguration conf) {
        final ScriptedSectionConfiguration repeatedConf;
        repeatedConf = new ScriptedSectionConfiguration(conf.type, conf.name, conf.parameters);
        repeatedConf.bodies.addAll(getRepeatedBodyConfigurations(conf.bodies));
        repeatedConf.actions.addAll(conf.actions);
        repeatedConf.flagsRequiredToStart.putAll(conf.flagsRequiredToStart);
        return repeatedConf;
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
            addRepeatedSubsection();
        }
    }

    private void addRepeatedSubsection() {
        ScriptedSection section = new ScriptedSection(world, repeatedConf, dialogueBox);
        sections.add(section);
    }

    private List<ScriptedBodyConfiguration> getRepeatedBodyConfigurations(List<ScriptedBodyConfiguration> configurations) {
        List<ScriptedBodyConfiguration> repeatedList = new LinkedList<>();
        for (ScriptedBodyConfiguration normalConfig : configurations) {
            ScriptedBodyConfiguration repeatedConfig = new ScriptedBodyConfiguration(0,normalConfig.line, normalConfig.name, normalConfig.path);
            repeatedConfig.hasPreviousSection = true;
            repeatedConfig.hasNextSection = true;
            repeatedList.add(repeatedConfig);
            repeatedConfig.actions.addAll(normalConfig.actions);
        }
        return repeatedList;
    }

    @Override
    public boolean isFinished() {
        return sections.isEmpty();
    }

    @Override
    public String toString() {
        return "   RecurringScriptedSection " + name + ":\n" +
                "    subsections (" + sections.size() + "): " +
                sections.stream().map(ScriptedSection::toString).collect(Collectors.joining("\n     "));
    }
}
