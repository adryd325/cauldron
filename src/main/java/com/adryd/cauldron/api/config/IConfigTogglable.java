package com.adryd.cauldron.api.config;

public interface IConfigTogglable {
    default void toggle() {
        toggle(false);
    }

    void toggle(boolean reverse);
}
