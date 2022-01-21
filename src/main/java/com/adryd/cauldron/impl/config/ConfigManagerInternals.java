package com.adryd.cauldron.impl.config;

import com.adryd.cauldron.api.config.ConfigFile;

import java.util.HashSet;
import java.util.Set;

public class ConfigManagerInternals {
    private static final Set<ConfigFile> configFiles = new HashSet<>();

    public static void writeAll() {
        for (ConfigFile file : configFiles) {
            file.write();
        }
    }

    public static void addConfigFile(ConfigFile file) {
        configFiles.add(file);
    }
}
