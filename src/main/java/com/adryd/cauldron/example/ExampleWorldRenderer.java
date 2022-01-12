package com.adryd.cauldron.example;

import com.adryd.cauldron.api.render.IWorldRenderHandler;
import com.adryd.cauldron.api.render.util.LineDrawing;
import com.adryd.cauldron.api.util.Color4f;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public class ExampleWorldRenderer implements IWorldRenderHandler {
    private boolean canRender;
    private VertexBuffer vbo;
    private final BufferBuilder buffer;

    public ExampleWorldRenderer() {
         this.buffer = new BufferBuilder(2097152);
    }

    @Override
    public void onWorldRender(MatrixStack matrices, Matrix4f positionMatrix, Camera camera, float tickDelta) {
        RenderSystem.disableTexture();
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.polygonOffset(-1f, -1f);
        RenderSystem.enablePolygonOffset();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SrcFactor.SRC_ALPHA,
                GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SrcFactor.ONE,
                GlStateManager.DstFactor.ZERO
        );
        RenderSystem.disableDepthTest();
        RenderSystem.lineWidth(6.0f);
        if (this.vbo == null) {
            this.vbo = new VertexBuffer();
        }
        this.buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
        LineDrawing.drawBox(new Box(new BlockPos(0,-54,0)), Vec3d.ZERO, new Color4f(1.0f,0.0f,0.0f,1.0f), buffer);
        this.buffer.end();
        this.vbo.submitUpload(buffer);
        matrices.push();
        matrices.translate(-camera.getPos().x, -camera.getPos().y, -camera.getPos().z);
        RenderSystem.setShader(GameRenderer::getRenderTypeLinesShader);
        this.vbo.setShader(matrices.peek().getPositionMatrix(), positionMatrix, GameRenderer.getRenderTypeLinesShader());
        matrices.pop();

        RenderSystem.polygonOffset(0f, 0f);
        RenderSystem.disablePolygonOffset();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
    }
}
