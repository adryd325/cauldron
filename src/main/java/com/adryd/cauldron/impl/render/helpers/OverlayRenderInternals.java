package com.adryd.cauldron.impl.render.helpers;

import com.adryd.cauldron.api.render.helper.IOverlayRenderHandler;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.profiler.Profiler;
import org.joml.Matrix4f;

import java.util.HashSet;
import java.util.Set;

public class OverlayRenderInternals {
    private static final Set<IOverlayRenderHandler> overlayRenderHandlers = new HashSet<>();

    public static void addRenderer(IOverlayRenderHandler renderer) {
        overlayRenderHandlers.add(renderer);
    }

    public static void afterChunkDebug(MatrixStack matrices, Matrix4f positionMatrix, Camera camera, float tickDelta, Profiler profiler) {
        profiler.swap("CauldronOverlayRenderers");
        if (!overlayRenderHandlers.isEmpty()) {
            // Update
            for (IOverlayRenderHandler overlayRenderer : overlayRenderHandlers) {
                overlayRenderer.setup();
                if (overlayRenderer.shouldUpdate(camera)) {
                    overlayRenderer.update(matrices, camera, tickDelta);
                }
            }

            float fogStart = RenderSystem.getShaderFogStart();
            BackgroundRenderer.clearFog();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

            // Render
            for (IOverlayRenderHandler overlayRenderer : overlayRenderHandlers) {
                if (overlayRenderer.shouldRender()) {
                    // Normalize
//                    RenderSystem.disableTexture();
                    RenderSystem.disableCull();
                    RenderSystem.enableDepthTest();
                    RenderSystem.depthMask(true);
                    RenderSystem.polygonOffset(-3f, -3f);
                    RenderSystem.enablePolygonOffset();

                    // TODO: Render objects will handle this
                    RenderSystem.lineWidth(6.0f);
                    overlayRenderer.render(tickDelta, camera);
                }
            }

            // Set back to as things were before
            RenderSystem.polygonOffset(0f, 0f);
            RenderSystem.disablePolygonOffset();
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
//            RenderSystem.enableTexture();
            RenderSystem.setShaderFogStart(fogStart);
        }
    }
}
