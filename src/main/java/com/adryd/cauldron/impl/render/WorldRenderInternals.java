package com.adryd.cauldron.impl.render;

import com.adryd.cauldron.LibCauldronClientMod;
import com.adryd.cauldron.LibCauldronMod;
import com.adryd.cauldron.api.render.IWorldRenderHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class WorldRenderInternals {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final List<IWorldRenderHandler> worldRenderHandlers = new ArrayList<>();

    public static void onWorldRender(MatrixStack matrices, Matrix4f positionMatrix, Camera camera, float tickDelta) {
        if (!worldRenderHandlers.isEmpty()) {
            client.getProfiler().swap("worldRenderersUsingLibCauldron");

            for (IWorldRenderHandler worldRenderHandler : worldRenderHandlers) {
                worldRenderHandler.onWorldRender(matrices, positionMatrix, camera, tickDelta);
            }

            // Swap back to what we were before
            client.getProfiler().swap("weather");
        }
    }

    public static void addWorldRenderer(IWorldRenderHandler worldRenderHandler) {
        worldRenderHandlers.add(worldRenderHandler);
    }

    public static void removeWorldRenderer(IWorldRenderHandler worldRenderHandler) {
        worldRenderHandlers.remove(worldRenderHandler);
    }

}
