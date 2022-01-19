package com.adryd.cauldron.api.config;

import net.minecraft.text.TranslatableText;

public abstract class ConfigOptionNestedBase extends ConfigOptionBase implements IConfigOptionNested {
    ConfigOptionNestedBase(String storageKey, TranslatableText displayName, TranslatableText description) {
        super(storageKey, displayName, description);
    }
}
