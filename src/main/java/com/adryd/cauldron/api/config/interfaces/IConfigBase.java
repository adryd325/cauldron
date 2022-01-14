package com.adryd.cauldron.api.config.interfaces;

import com.google.gson.JsonElement;
import net.minecraft.text.TranslatableText;

public interface IConfigBase {
    String getStorageKey();

    TranslatableText getName();

    TranslatableText getDescription();

    boolean isModified();

    void resetToDefault();

    JsonElement toJSON();

    void fromJSON(JsonElement json);
}
