package com.adryd.cauldron.api.config;

import com.google.gson.JsonObject;
import net.minecraft.text.TranslatableText;

public interface IConfigOption {
    JsonObject toJsonObject();

    void fromJsonObject(JsonObject element);

    boolean isModified();

    void resetToDefault();

    String getStorageKey();

    TranslatableText getDisplayName();
}
