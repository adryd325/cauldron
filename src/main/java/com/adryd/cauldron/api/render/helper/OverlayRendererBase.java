package com.adryd.cauldron.api.render.helper;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public abstract class OverlayRendererBase implements IOverlayRenderHandler {
    protected final List<RenderObject> renderObjects;

    public OverlayRendererBase() {
        this.renderObjects = new ArrayList<>();
    }

    @Override
    public void render(MatrixStack matrices, Matrix4f positionMatrix, float tickDelta) {
        for (RenderObject renderObject : renderObjects) {
            renderObject.draw(matrices, positionMatrix);
        }
    }

    @Override
    public void setup() {
        for (RenderObject renderObject : this.renderObjects) {
            renderObject.setup();
        }
    }
}
