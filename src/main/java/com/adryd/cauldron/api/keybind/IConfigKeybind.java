package com.adryd.cauldron.api.keybind;

import com.adryd.cauldron.api.config.interfaces.IConfigBase;

public interface IConfigKeybind extends IConfigBase {
    void setSettings(KeybindSettings settings);

    KeybindSettings getSettings();
}
