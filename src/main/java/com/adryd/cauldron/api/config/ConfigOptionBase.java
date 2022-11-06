package com.adryd.cauldron.api.config;

public abstract class ConfigOptionBase<T extends IConfigOption> implements IConfigOption {
    private final String key;
    private final String name;

    public ConfigOptionBase(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }
}
