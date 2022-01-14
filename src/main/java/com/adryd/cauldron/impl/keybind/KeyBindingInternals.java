package com.adryd.cauldron.impl.keybind;

import java.util.HashSet;
import java.util.Set;

public class KeyBindingInternals {

    private static final Set<Integer> heldKeys = new HashSet<>();
    private static boolean hasRun = false;

    public static void onKey(int key, int action) {
        if (action == 1) {
            heldKeys.add(key);
        } else if (action == 0) {
            heldKeys.remove(key);
        }
    }
}
