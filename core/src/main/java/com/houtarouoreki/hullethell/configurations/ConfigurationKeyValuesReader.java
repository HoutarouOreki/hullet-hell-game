package com.houtarouoreki.hullethell.configurations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationKeyValuesReader {
    public static Map<String, String> getKeyValues(List<String> lines) {
        Map<String, String> returnPairs = new HashMap<String, String>();
        for (String line : lines) {
            String[] strings = line.split(": ");
            returnPairs.put(strings[0], strings[1]);
        }
        return returnPairs;
    }
}
