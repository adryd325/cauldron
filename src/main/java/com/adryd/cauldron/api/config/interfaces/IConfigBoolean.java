package com.adryd.cauldron.api.config.interfaces;

public interface IConfigBoolean extends IConfigBase {
    boolean getBooleanValue();

    boolean getDefaultBooleanValue();

    void setBooleanValue(boolean value);

    default void toggleBooleanValue()
    {
        this.setBooleanValue(! this.getBooleanValue());
    }
}
