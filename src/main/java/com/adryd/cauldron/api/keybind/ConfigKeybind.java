package com.adryd.cauldron.api.keybind;

import com.adryd.cauldron.api.config.ConfigBase;
import com.google.gson.JsonElement;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class ConfigKeybind extends ConfigBase<ConfigKeybind> implements IConfigKeybind {
    private final List<Integer> defaultKeybind;
    private final KeybindSettings defaultSettings;
    private List<Integer> keybind;
    private KeybindSettings settings;

    public ConfigKeybind(String storageKey, TranslatableText name, TranslatableText description, List<Integer> defaultValue, KeybindSettings defaultSetings) {
        super(storageKey, description, name);
        this.defaultKeybind = defaultValue;
        this.defaultSettings = defaultSetings;
        this.keybind = defaultValue;
        this.settings = defaultSettings;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void resetToDefault() {

    }

    @Override
    public JsonElement toJSON() {
        return null;
    }

    @Override
    public void fromJSON(JsonElement json) {

    }

    @Override
    public void setSettings(KeybindSettings settings) {

    }

    @Override
    public KeybindSettings getSettings() {
        return null;
    }
}
