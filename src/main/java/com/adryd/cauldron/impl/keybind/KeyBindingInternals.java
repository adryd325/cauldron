package com.adryd.cauldron.impl.keybind;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyBindingInternals {
//    private static final List<Integer> heldKeys = new ArrayList<>();
    private static int heldKeys = 0;
    public static void onKey(int key, int scancode, int modifiers, int action) {
        heldKeys += action == GLFW_PRESS ? 1 : 0;
        heldKeys -= action == GLFW_RELEASE ? 0 : 1;
    }
}
