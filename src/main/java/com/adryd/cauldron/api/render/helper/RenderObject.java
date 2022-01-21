package com.adryd.cauldron.api.render.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

import java.util.function.Supplier;

public class RenderObject {
    protected VertexFormat.DrawMode drawMode;
    protected VertexFormat vertexFormat;
    protected Supplier<Shader> shaderSupplier;
    protected BufferBuilderProxy bufferBuilder;
    private VertexBuffer vertexBuffer;

    public RenderObject(VertexFormat.DrawMode drawMode, VertexFormat vertexFormat, Supplier<Shader> shaderSupplier) {
        this.bufferBuilder = new BufferBuilderProxy(BufferBuilderProxy.MAX_BUFFER_SIZE);
        this.drawMode = drawMode;
        this.vertexFormat = vertexFormat;
        this.shaderSupplier = shaderSupplier;
    }

    public BufferBuilderProxy startBuffer() {
        this.bufferBuilder.begin(this.drawMode, this.vertexFormat);
        return this.bufferBuilder;
    }

    public void endBuffer() {
        this.bufferBuilder.end();
        this.vertexBuffer.submitUpload(bufferBuilder);
    }

    public void draw(MatrixStack matrices, Matrix4f positionMatrix) {
        this.beforeDraw();
        RenderSystem.setShader(this.shaderSupplier);
        this.vertexBuffer.setShader(matrices.peek().getPositionMatrix(), positionMatrix, this.shaderSupplier.get());
        this.afterDraw();
    }

    protected void afterDraw() {
    }

    protected void beforeDraw() {
    }

    public void setup() {
        if (this.vertexBuffer != null) return;
        this.vertexBuffer = new VertexBuffer();
    }
}
