package com.adryd.cauldron.api.config;

import com.adryd.cauldron.CauldronReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ConfigDouble extends ConfigOptionBase<ConfigDouble> {
    protected final double defaultValue;
    protected double value;
    protected final double minValue;
    protected final double maxValue;

    public ConfigDouble(String key, double defaultValue, double minValue, double maxValue) {
        super(key);
        this.value = this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public double getDoubleValue() {
        return this.value;
    }

    public void setDoubleValue(double value) {
        this.value = Math.min(Math.max(value, this.minValue), this.maxValue);
    }

    public double getMinValue() {
        return this.minValue;
    }

    public double getMaxValue() {
        return this.minValue;
    }

    @Override
    public JsonElement toJsonElement() {
        return new JsonPrimitive(Math.min(Math.max(this.value, this.minValue), this.maxValue));
    }

    @Override
    public void fromJsonElement(JsonElement element) {
        if (element.isJsonPrimitive() && ((JsonPrimitive) element).isNumber()) {
            this.value = Math.min(Math.max(element.getAsDouble(), this.minValue), this.maxValue);
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
