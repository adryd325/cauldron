package com.adryd.cauldron.api.config;

import com.adryd.cauldron.api.config.interfaces.IConfigCallback;
import com.adryd.cauldron.api.config.interfaces.IConfigBase;
import com.adryd.cauldron.api.config.interfaces.IConfigWithCallback;
import net.minecraft.text.TranslatableText;

public abstract class ConfigBase<T extends IConfigBase> implements IConfigBase, IConfigWithCallback<T> {

    private final String storageKey;
    private final TranslatableText name;
    private final TranslatableText description;
    private IConfigCallback<T> callback;

    protected ConfigBase(String storageKey, TranslatableText name, TranslatableText description) {
        this.storageKey = storageKey;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getStorageKey() {
        return this.storageKey;
    }

    @Override
    public TranslatableText getName() {
        return this.name;
    }

    @Override
    public TranslatableText getDescription() {
        return this.description;
    }

    @Override
    public void onValueChanged() {
        if (this.callback != null) {
            this.callback.onValueChanged((T) this);
        }
    }

    @Override
    public void setValueChangedCallback(IConfigCallback<T> callback) {
        this.callback = callback;
    }
}
