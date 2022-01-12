package com.adryd.cauldron.api.render;

import net.minecraft.client.util.math.MatrixStack;

public interface IHUDRenderHandler {
    void onHUDRender(MatrixStack matrices, float tickDelta);
}
