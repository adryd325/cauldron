package com.adryd.cauldron.impl.render;

import com.adryd.cauldron.api.render.IHUDRenderHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashSet;
import java.util.Set;

public class HUDRenderInternals {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final Set<IHUDRenderHandler> hudRenderHandlers = new HashSet<>();

    public static void onHUDRender(MatrixStack matrices, float tickDelta) {
        if (!hudRenderHandlers.isEmpty()) {
            client.getProfiler().push("hudRenderersUsingLibCauldron");

            for (IHUDRenderHandler hudRenderHandler : hudRenderHandlers) {
                hudRenderHandler.onHUDRender(matrices, tickDelta);
            }

            client.getProfiler().pop();
        }
    }

    public static void addHUDRenderer(IHUDRenderHandler hudRenderHandler) {
        hudRenderHandlers.add(hudRenderHandler);
    }

    public static void removeHUDRenderer(IHUDRenderHandler hudRenderHandler) {
        hudRenderHandlers.remove(hudRenderHandler);
    }
}