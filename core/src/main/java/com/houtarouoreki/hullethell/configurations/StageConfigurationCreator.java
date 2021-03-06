package com.houtarouoreki.hullethell.configurations;

import com.houtarouoreki.hullethell.bindables.Bindable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StageConfigurationCreator {
    public final List<ScriptedSectionConfiguration> sections = new ArrayList<>(50);
    final HashMap<String, ScriptedBodyConfiguration> allBodies = new HashMap<>(100);
    private final Pattern sectionPattern = Pattern.compile("^!(?<type>\\w*)(?:\\(?(?<params>.*)\\))?(?: ?(?<name>\\w*))?(?: ?@(?<startFlags>.*))?$");
    private final Pattern dialogueSectionCharacterPattern = Pattern.compile("^(?<characterName>.*):$");
    private final Pattern dialogueSectionTextPattern = Pattern.compile("\\s(?<text>.*)");
    private final Pattern bodyPattern = Pattern.compile("^(?<name>\\w+):(?:\\s+\"(?<path>.*)\"$)?");
    private final Pattern actionPattern = Pattern.compile("^[ \\t]*(?<time>\\d\\.?\\d*)\\s+(?<name>\\w+):?(?:[ \\t]+(?<arguments>.*))?$");
    private final Bindable<ScriptedSectionConfiguration> currentSection = new Bindable<>(null);
    private final Bindable<ScriptedBodyConfiguration> currentBody = new Bindable<>(null);

    public StageConfigurationCreator(List<String> lines) {
        currentBody.addListener(this::sortPreviousBody);
        currentSection.addListener(this::sortPreviousSection);
        generate(lines);
    }

    private void generate(List<String> lines) {
        Matcher matcher;
        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;
            if (line.startsWith("#"))
                continue;
            if ((matcher = sectionPattern.matcher(line)).matches()) {
                currentSection.setValue(new ScriptedSectionConfiguration(matcher.group("type"),
                        matcher.group("name"), matcher.group("params")));
                setStartFlags(currentSection.getValue(), matcher.group("startFlags"));
                sections.add(currentSection.getValue());
                currentBody.setValue(null);
                continue;
            }
            if (currentSection.getValue() == null)
                continue;

            if (currentSection.getValue().type.equals("dialogue"))
                handleDialogueSection(line, lineNumber);
            else {
                handleNormalOrWhileSection(line, lineNumber);
            }
        }
        currentBody.setValue(null);
        currentSection.setValue(null);
    }

    private void sortPreviousSection(ScriptedSectionConfiguration prev, ScriptedSectionConfiguration next) {
        if (prev == null)
            return;
        prev.actions.sort(null);
        prev.bodies.sort(null);
    }

    private void sortPreviousBody(ScriptedBodyConfiguration prev,ScriptedBodyConfiguration next) {
        if (prev == null)
            return;
        prev.actions.sort(null);
    }

    private void handleNormalOrWhileSection(String line, int lineNumber) {
        handleBodyLine(line, lineNumber);
        handleActionLine(line, lineNumber);
    }

    private void handleActionLine(String line, int lineNumber) {
        Matcher matcher = actionPattern.matcher(line);
        if (!matcher.matches())
            return;
        ScriptedActionConfiguration action = new ScriptedActionConfiguration(lineNumber,
                matcher.group("name"),
                getArgumentsList(matcher.group("arguments")),
                Double.parseDouble(matcher.group("time")),
                line
        );
        if (line.startsWith("\t"))
            currentBody.getValue().actions.add(action);
        else
            currentSection.getValue().actions.add(action);
    }

    private List<String> getArgumentsList(String s) {
        if (s != null)
            return Arrays.asList(s
                    // this regex splits only if the pattern is not followed by
                    // digits and closing parenthesis on the right,
                    // so (321, 3211.43) for example won't be split
                    .split(", (?!-?\\d+(?:\\.\\d+)?\\))"));
        return new ArrayList<>();
    }

    private void handleBodyLine(String line, int lineNumber) {
        Matcher matcher = bodyPattern.matcher(line);
        if (!matcher.matches())
            return;
        String bodyName = matcher.group("name");
        boolean hasBodyName = bodyName != null && !bodyName.isEmpty();
        String path = matcher.group("path");
        if (hasBodyName && path == null)
            path = allBodies.get(bodyName).path;

        if (hasBodyName && allBodies.containsKey(bodyName))
            allBodies.get(bodyName).hasNextSection = true;

        currentBody.setValue(new ScriptedBodyConfiguration(lineNumber, line, bodyName, path));

        if (hasBodyName && allBodies.containsKey(bodyName))
            currentBody.getValue().hasPreviousSection = true;

        if (hasBodyName)
            allBodies.put(bodyName, currentBody.getValue());

        if (currentSection.getValue().type.equals("recurring") || currentSection.getValue().type.equals("while"))
            currentBody.getValue().hasNextSection = true;

        currentSection.getValue().bodies.add(currentBody.getValue());
    }

    private void handleDialogueSection(String line, int lineNumber) {
        if (!handleDialogueCharacterLine(line, lineNumber))
            handleDialogueTextLine(line, lineNumber);
    }

    private void handleDialogueTextLine(String line, int lineNumber) {
        Matcher matcher = dialogueSectionTextPattern.matcher(line);
        if (!matcher.matches())
            return;
        ScriptedActionConfiguration dialogueTextAction = new ScriptedActionConfiguration(lineNumber,
                "dialogueText",
                Collections.singletonList(matcher.group("text")),
                0, line);
        currentSection.getValue().actions.add(dialogueTextAction);
    }

    private boolean handleDialogueCharacterLine(String line, int lineNumber) {
        Matcher matcher = dialogueSectionCharacterPattern.matcher(line);
        if (!matcher.matches())
            return false;
        ScriptedActionConfiguration dialogueCharacterAction = new ScriptedActionConfiguration(lineNumber,
                "dialogueCharacter",
                Collections.singletonList(matcher.group("characterName")),
                0, line);
        currentSection.getValue().actions.add(dialogueCharacterAction);
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
