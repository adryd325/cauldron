package com.adryd.cauldron.impl.render.helpers;

import com.adryd.cauldron.api.render.helper.IOverlayRenderHandler;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

import java.util.HashSet;
import java.util.Set;

public class OverlayRenderInternals {
    private static final Set<IOverlayRenderHandler> overlayRenderHandlers = new HashSet<>();

    public static void addRenderer(IOverlayRenderHandler renderer) {
        overlayRenderHandlers.add(renderer);
    }

    public static void afterEntities(WorldRenderContext context) {
        context.profiler().swap("CauldronOverlayRenderers");
        MatrixStack matrices = context.matrixStack();
        Matrix4f positionMatrix = context.projectionMatrix();
        Camera camera = context.camera();
        float tickDelta = context.tickDelta();
        if (!overlayRenderHandlers.isEmpty()) {
            // Update
            for (IOverlayRenderHandler overlayRenderer : overlayRenderHandlers) {
                overlayRenderer.setup();
                if (overlayRenderer.shouldUpdate()) {
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
                    RenderSystem.disableTexture();
                    RenderSystem.disableCull();
                    RenderSystem.enableDepthTest();
                    RenderSystem.depthMask(false);
                    RenderSystem.polygonOffset(-3f, -3f);
                    RenderSystem.enablePolygonOffset();

                    // TODO: Render objects will handle this
                    RenderSystem.lineWidth(6.0f);
                    matrices.push();
                    matrices.translate(-camera.getPos().x, -camera.getPos().y, -camera.getPos().z);
                    overlayRenderer.render(matrices, positionMatrix, tickDelta);
                    matrices.pop();
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
            RenderSystem.enableTexture();
            RenderSystem.setShaderFogStart(fogStart);
        }
    }
}
