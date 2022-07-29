package com.adryd.cauldron.api.render.helper;

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
        this.vertexBuffer.bind();
        this.vertexBuffer.upload(this.bufferBuilder.end());
        VertexBuffer.unbind();
    }

    public void draw(MatrixStack matrices, Matrix4f positionMatrix) {
        this.beforeDraw();
        this.vertexBuffer.bind();
        this.vertexBuffer.draw(matrices.peek().getPositionMatrix(), positionMatrix, this.shaderSupplier.get());
        VertexBuffer.unbind();
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
