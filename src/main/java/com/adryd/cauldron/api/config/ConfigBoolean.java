package com.adryd.cauldron.api.config;

import com.adryd.cauldron.api.config.interfaces.IConfigBoolean;
import com.google.gson.JsonElement;
import net.minecraft.text.TranslatableText;

public class ConfigBoolean extends ConfigBase<ConfigBoolean> implements IConfigBoolean {
    private final boolean defaultValue;
    private boolean value;

    public ConfigBoolean(String storageKey, TranslatableText name, TranslatableText description, boolean defaultValue) {
        super(storageKey, description, name);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public boolean getBooleanValue() {
        return this.value;
    }

    @Override
    public void setBooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean getDefaultBooleanValue() {
        return this.defaultValue;
    }

    @Override
    public boolean isModified() {
        return this.value != this.defaultValue;
    }

    @Override
    public void resetToDefault() {
        this.value = this.defaultValue;
    }

    @Override
    public JsonElement toJSON() {
        return null;
    }

    @Override
    public void fromJSON(JsonElement json) {
    }
}
