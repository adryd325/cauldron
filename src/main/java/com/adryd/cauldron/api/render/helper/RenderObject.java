package com.adryd.cauldron.api.render.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

import java.util.function.Supplier;

public class RenderObject {
    protected VertexFormat.DrawMode drawMode;
    protected VertexFormat vertexFormat;
    protected Supplier<Shader> shaderSupplier;
    protected BufferBuilderProxy bufferBuilder;
    private VertexBuffer vertexBuffer;

    private Vec3d lastDrawCameraPos;


    public RenderObject(VertexFormat.DrawMode drawMode, VertexFormat vertexFormat, Supplier<Shader> shaderSupplier) {
        this.bufferBuilder = new BufferBuilderProxy(BufferBuilderProxy.MAX_BUFFER_SIZE);
        this.drawMode = drawMode;
        this.vertexFormat = vertexFormat;
        this.shaderSupplier = shaderSupplier;
        this.lastDrawCameraPos = Vec3d.ZERO;
    }

    public BufferBuilderProxy startBuffer() {
        this.bufferBuilder.begin(this.drawMode, this.vertexFormat);
        return this.bufferBuilder;
    }

    public void endBuffer(Camera camera) {
        // Set last draw camera position
        this.lastDrawCameraPos = camera.getPos();
        this.vertexBuffer.bind();
        this.vertexBuffer.upload(this.bufferBuilder.end());
        VertexBuffer.unbind();
    }

    public void draw(float tickDelta, Camera camera) {
        this.beforeDraw();

        // Set shader
        RenderSystem.setShader(this.shaderSupplier);

        // Get model view stack
        MatrixStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.push();
        // Translate to new camera position
        modelViewStack.translate(this.lastDrawCameraPos.x-camera.getPos().x, this.lastDrawCameraPos.y-camera.getPos().y, this.lastDrawCameraPos.z-camera.getPos().z);

        // Draw
        this.vertexBuffer.bind();
        this.vertexBuffer.draw(modelViewStack.peek().getPositionMatrix(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
        VertexBuffer.unbind();

        modelViewStack.pop();

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
