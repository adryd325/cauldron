package com.adryd.cauldron.api.render.helper;

import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;

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
