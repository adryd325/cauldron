package com.adryd.cauldron.example;

import com.adryd.cauldron.api.render.helper.BufferBuilderProxy;
import com.adryd.cauldron.api.render.helper.OverlayRendererBase;
import com.adryd.cauldron.api.render.helper.RenderObject;
import com.adryd.cauldron.api.render.util.LineDrawing;
import com.adryd.cauldron.api.render.util.QuadDrawing;
import com.adryd.cauldron.api.util.Color4f;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ExampleOverlayRenderer extends OverlayRendererBase {

    private boolean shouldUpdate;

    public ExampleOverlayRenderer() {
        this.shouldUpdate = true;
        this.renderObjects.add(new RenderObject(VertexFormat.DrawMode.LINES, VertexFormats.LINES, GameRenderer::getRenderTypeLinesShader));
        this.renderObjects.add(new RenderObject(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR, GameRenderer::getPositionColorShader));
    }

    public boolean shouldUpdate() {
        return this.shouldUpdate;
    }

    @Override
    public void update(MatrixStack matrices, Camera camera, float tickDelta) {
        RenderObject lines = this.renderObjects.get(0);
        RenderObject quads = this.renderObjects.get(1);

        BufferBuilderProxy linesBuf = lines.startBuffer();
        BufferBuilderProxy quadsBuf = quads.startBuffer();

        LineDrawing.drawBox(new Box(new BlockPos(0, -56, 0)), Vec3d.ZERO, new Color4f(0f, 0f, 1f, 1f), linesBuf);
        QuadDrawing.drawBox(new Box(new BlockPos(0, -56, 0)), Vec3d.ZERO, new Color4f(0f, 0f, 1f, 0.1f), quadsBuf);

        lines.endBuffer();
        quads.endBuffer();

        this.shouldUpdate = false;
    }
}
