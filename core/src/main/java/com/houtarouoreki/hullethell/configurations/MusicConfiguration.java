package com.houtarouoreki.hullethell.configurations;

import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MusicConfiguration {
    public final String author;
    public final String title;

    public MusicConfiguration(FileHandle file) {
        this(Arrays.asList(new String(file.readBytes()).split("\\r?\\n")));
    }

    public MusicConfiguration(List<String> configurationLines) {
        Map<String, String> keyValues = ConfigurationsHelper.getKeyValues(configurationLines);
        author = keyValues.get("author");
        title = keyValues.get("title");
    }

    @Override
    public String toString() {
        return author + " - " + title;
    }
}
