package com.adryd.cauldron.api.config.interfaces;

public interface IConfigBannable {
    boolean isBanned();

    boolean getNextLaunchValue();

    void toggleBannedState();
}
