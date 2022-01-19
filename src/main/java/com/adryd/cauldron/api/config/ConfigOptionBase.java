package com.adryd.cauldron.api.config;

import net.minecraft.text.TranslatableText;

public abstract class ConfigOptionBase implements IConfigOption {
    private final String storageKey;
    protected TranslatableText displayName;
    protected TranslatableText description;

    ConfigOptionBase(String storageKey, TranslatableText displayName, TranslatableText description) {
        this.storageKey = storageKey;
        this.displayName = displayName;
        this.description = description;
    }

    @Override
    public String getStorageKey() {
        return this.storageKey;
    }

    @Override
    public TranslatableText getDisplayName() {
        return this.displayName;
    }

    public TranslatableText getDescription() {
        return this.description;
    }

    public void setDescription(TranslatableText description) {
        this.description = description;
    }
}
