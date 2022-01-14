package com.adryd.cauldron.api.config;

import com.adryd.cauldron.api.config.interfaces.IConfigBannable;
import com.google.gson.JsonElement;
import net.minecraft.text.TranslatableText;

public class ConfigBooleanBannable extends ConfigBoolean implements IConfigBannable {
    private boolean banned;
    private boolean nextLaunchValue;

    public ConfigBooleanBannable(String storageKey, TranslatableText name, TranslatableText description, boolean defaultValue) {
        super(storageKey, name, description, defaultValue);
    }

    @Override
    public boolean isBanned() {
        return this.banned;
    }

    @Override
    public boolean getNextLaunchValue() {
        return this.nextLaunchValue;
    }

    @Override
    public void toggleBannedState() {
        this.nextLaunchValue = !this.nextLaunchValue;
    }

    @Override
    public JsonElement toJSON() {
        return null;
    }

    @Override
    public void fromJSON(JsonElement json) {
    }
}
