package com.adryd.cauldron.api.config.interfaces;

public interface IConfigWithCallback<T extends IConfigBase> {
    void onValueChanged();

    void setValueChangedCallback(IConfigCallback<T> callback);
}
