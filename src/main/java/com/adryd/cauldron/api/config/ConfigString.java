package com.adryd.cauldron.api.config;

import com.adryd.cauldron.CauldronReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConfigString extends ConfigOptionBase<ConfigString> {
    protected final String defaultValue;
    protected String value;

    public ConfigString(String key, String name, String defaultValue) {
        super(key, name);
        this.value = this.defaultValue = defaultValue;
    }

    public String getStringValue() {
        return this.value;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public JsonElement toJsonElement() {
        return new JsonPrimitive(this.value);
    }

    @Override
    public void fromJsonElement(JsonElement element) {
        if (element.isJsonPrimitive() && ((JsonPrimitive) element).isString()) {
            this.value = element.getAsString();
        } else {
            CauldronReference.LOGGER.warn("Failed to read storage key \"{}\" as string", this.getKey());
        }
    }

    @Override
    public boolean isModified() {
        return this.value.equals(this.defaultValue);
    }

    @Override
    public void resetToDefault() {
        this.value = this.defaultValue;
    }
}
