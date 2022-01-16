package com.adryd.cauldron.impl.keybind;

import java.util.ArrayList;
import java.util.List;

public class KeyBindingInternals {
    private static final List<Integer> heldKeys = new ArrayList<>();
    public static void onKey(int key, int action) {
        if (action == 1) {
            heldKeys.add(key);
        } else if (action == 0) {
            heldKeys.remove(key);
        }
    }
}
