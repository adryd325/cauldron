package com.adryd.cauldron.api.config;

public abstract class ConfigOptionBase<T extends IConfigOption> implements IConfigOption {
    private final String key;

    public ConfigOptionBase(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
