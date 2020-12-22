package com.houtarouoreki.hullethell.configurations;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StageConfigurationCreator {
    public final List<ScriptedSectionConfiguration> sections = new ArrayList<>();
    final HashMap<String, ScriptedBodyConfiguration> allBodies = new HashMap<>();
    private final Pattern sectionPattern = Pattern.compile("^!(?<type>\\w*)(?:\\(?(?<whileParams>.*)\\))?(?: ?(?<name>\\w*))?(?: ?@(?<startFlags>.*))?$");
    private final Pattern dialogueSectionCharacterPattern = Pattern.compile("^(?<characterName>.*):$");
    private final Pattern dialogueSectionTextPattern = Pattern.compile("\\t(?<text>.*)");
    private final Pattern bodyPattern = Pattern.compile("^(?<name>\\w+):(?:\\s+\"(?<path>.*)\"$)?");
    private final Pattern actionPattern = Pattern.compile("^\\t*(?<time>\\d\\.?\\d*)\\s+(?<name>\\w+):?\\s+(?<arguments>.*)$");
    private ScriptedSectionConfiguration currentSection;
    private ScriptedBodyConfiguration currentBody;

    public StageConfigurationCreator(List<String> lines) {
        generate(lines);
    }

    private void generate(List<String> lines) {
        Matcher matcher;
        for (String line : lines) {
            if (line.startsWith("#"))
                continue;
            if ((matcher = sectionPattern.matcher(line)).matches()) {
                currentSection = new ScriptedSectionConfiguration(matcher.group("type"),
                        matcher.group("name"), matcher.group("whileParams"));
                setStartFlags(currentSection, matcher.group("startFlags"));
                sections.add(currentSection);
                continue;
            }
            if (currentSection == null)
                continue;

            if (currentSection.type.equals("dialogue"))
                handleDialogueSection(line);
            else {
                handleNormalOrWhileSection(line);
            }
        }
    }

    private void handleNormalOrWhileSection(String line) {
        handleBodyLine(line);
        handleActionLine(line);
    }

    private void handleActionLine(String line) {
        Matcher matcher = actionPattern.matcher(line);
        if (!matcher.matches())
            return;
        ScriptedActionConfiguration action = new ScriptedActionConfiguration(
                matcher.group("name"),
                Arrays.asList(matcher.group("arguments").split(", ").clone()),
                Double.parseDouble(matcher.group("time")),
                line
        );
        if (line.startsWith("\t"))
            currentBody.actions.add(action);
        else
            currentSection.actions.add(action);
    }

    private void handleBodyLine(String line) {
        Matcher matcher = bodyPattern.matcher(line);
        if (!matcher.matches())
            return;
        String bodyName = matcher.group("name");

        if (allBodies.containsKey(bodyName))
            allBodies.get(bodyName).hasNextSection = true;

        currentBody = new ScriptedBodyConfiguration(
                line, bodyName, matcher.group("path"));

        if (allBodies.containsKey(bodyName))
            currentBody.hasPreviousSection = true;

        allBodies.put(bodyName, currentBody);

        currentSection.bodies.add(currentBody);
    }

    private void handleDialogueSection(String line) {
        if (!handleDialogueCharacterLine(line))
            handleDialogueTextLine(line);
    }

    private void handleDialogueTextLine(String line) {
        Matcher matcher = dialogueSectionTextPattern.matcher(line);
        if (!matcher.matches())
            return;
        ScriptedActionConfiguration dialogueTextAction = new ScriptedActionConfiguration(
                "dialogueText",
                Collections.singletonList(matcher.group("text")),
                0, line);
        currentSection.actions.add(dialogueTextAction);
    }

    private boolean handleDialogueCharacterLine(String line) {
        Matcher matcher = dialogueSectionCharacterPattern.matcher(line);
        if (!matcher.matches())
            return false;
        ScriptedActionConfiguration dialogueCharacterAction = new ScriptedActionConfiguration(
                "dialogueCharacter",
                Collections.singletonList(matcher.group("characterName")),
                0, line);
        currentSection.actions.add(dialogueCharacterAction);
        return true;
    }

    private void setStartFlags(ScriptedSectionConfiguration section, String flagsString) {
        if (flagsString == null || flagsString.isEmpty())
            return;
        String[] flagsAndAmounts = flagsString.split("\\s+");
        for (String flag : flagsAndAmounts) {
            String[] info = flag.split("\\*");
            if (info.length == 1)
                section.flagsRequiredToStart.put(flag, 1);
            else
                section.flagsRequiredToStart.put(info[1], Integer.parseInt(info[0]));
        }
    }
}
