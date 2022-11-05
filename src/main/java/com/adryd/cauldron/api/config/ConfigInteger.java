package com.adryd.cauldron.api.config;

import com.adryd.cauldron.CauldronReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConfigInteger extends ConfigOptionBase<ConfigInteger> {
    protected final int defaultValue;
    protected int value;
    protected final int minValue;
    protected final int maxValue;

    public ConfigInteger(String key, String name, int defaultValue, int minValue, int maxValue) {
        super(key, name);
        this.value = this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getIntegerValue() {
        return this.value;
    }

    public int getMinValue() {
        return this.minValue;
    }

    public int getMaxValue() {
        return this.minValue;
    }

    @Override
    public JsonElement toJsonElement() {
        return new JsonPrimitive(Math.min(Math.max(this.value, this.minValue), this.maxValue));
    }

    @Override
    public void fromJsonElement(JsonElement element) {
        if (element.isJsonPrimitive() && ((JsonPrimitive) element).isNumber()) {
            this.value = Math.min(Math.max(element.getAsInt(), this.minValue), this.maxValue);
        } else {
            CauldronReference.LOGGER.warn("Failed to read storage key \"{}\" as integer", this.getKey());
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
}
