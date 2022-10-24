package com.adryd.cauldron.api.config;

import com.adryd.cauldron.api.keybind.KeybindAction;
import com.adryd.cauldron.api.keybind.KeybindContext;
import com.google.gson.JsonElement;

public class ConfigKeybind extends ConfigOptionBase<ConfigKeybind> implements IConfigOption {
    private final KeybindAction defaultAction;
    private final KeybindContext defaultContext;

    private KeybindAction action;
    private KeybindContext context;

    public ConfigKeybind(String configKey, String keyBind, KeybindAction action, KeybindContext context) {
        super(configKey);

        this.action = this.defaultAction = action;
        this.context = this.defaultContext = context;
    }

    @Override
    public JsonElement toJsonElement() {
        return null;
    }

    @Override
    public void fromJsonElement(JsonElement element) {

    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void resetToDefault() {

    }
}
