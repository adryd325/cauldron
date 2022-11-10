package com.adryd.cauldron.api.render.helper;

import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public interface IOverlayRenderHandler {

    default boolean shouldUpdate(Camera camera) {
        return true;
    }

    default boolean shouldRender() {
        return true;
    }

    void update(MatrixStack matrices, Camera camera, float tickDelta);

    void render(float tickDelta, Camera camera);

    void setup();
}
