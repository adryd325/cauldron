package com.adryd.cauldron.api.util;

public class Color4f {
    public float r;
    public float g;
    public float b;
    public float a;

    public Color4f(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    Color4f(float r, float g, float b) {
        this(r, g, b, 1.0f);
    }
}
