package com.adryd.cauldron.api.config;

import com.adryd.cauldron.impl.config.ConfigManagerInternals;

public class ConfigManager {
    public static void addConfigFile(ConfigFile file) {
        ConfigManagerInternals.addConfigFile(file);
    }
}