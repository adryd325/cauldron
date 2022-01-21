package com.adryd.cauldron.api.config;

import com.google.gson.JsonElement;

public interface IConfigOption {
    JsonElement toJsonElement();

    void fromJsonElement(JsonElement element);

    boolean isModified();

    void resetToDefault();

    String getKey();
}
