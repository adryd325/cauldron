package com.adryd.cauldron.api.render;

import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public interface IWorldRenderHandler {
    void onWorldRender(MatrixStack matrices, Matrix4f positionMatrix, Camera camera, float tickDelta);
}
