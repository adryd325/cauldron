package com.adryd.cauldron.api.config;

import com.adryd.cauldron.CauldronReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConfigBoolean extends ConfigOptionBase<ConfigBoolean> implements IConfigTogglable {
    protected final boolean defaultValue;
    protected boolean value;

    public ConfigBoolean(String key, boolean defaultValue) {
        super(key);
        this.value = this.defaultValue = defaultValue;
    }

    public boolean getBooleanValue() {
        return this.value;
    }

    public boolean getDefaultValue() {
        return this.defaultValue;
    }


    @Override
    public JsonElement toJsonElement() {
        return new JsonPrimitive(this.value);
    }

    @Override
    public void fromJsonElement(JsonElement element) {
        if (element.isJsonPrimitive() && ((JsonPrimitive) element).isBoolean()) {
            this.value = element.getAsBoolean();
        } else {
            CauldronReference.LOGGER.warn("Failed to read storage key \"{}\" as boolean", this.getKey());
        }
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
    public void toggle(boolean reverse) {
        this.value = !this.value;
    }
}
