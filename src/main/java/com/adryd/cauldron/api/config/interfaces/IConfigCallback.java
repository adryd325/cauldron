package com.adryd.cauldron.api.config.interfaces;

public interface IConfigCallback<T extends IConfigBase> {
    void onValueChanged(T value);
}
