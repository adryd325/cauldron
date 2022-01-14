package com.adryd.cauldron.example;

import com.adryd.cauldron.api.render.IHUDRenderHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class ExampleHUDRenderer implements IHUDRenderHandler {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onHUDRender(MatrixStack matrices, float tickDelta) {
        if (!client.options.debugEnabled)
            client.textRenderer.drawWithShadow(matrices, "Example HUD renderer from libunnamed", 5, 5, 0xFFFFFFFF);
    }
}
