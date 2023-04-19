package com.adryd.cauldronTest.example;

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

public class ExampleOverlayRenderer extends OverlayRendererBase {

    private final RenderObject lines;
    private final RenderObject quads;

    public ExampleOverlayRenderer() {
        this.shouldUpdate = true;
        this.lines = new RenderObject(VertexFormat.DrawMode.LINES, VertexFormats.LINES, GameRenderer::getRenderTypeLinesProgram);
        this.quads = new RenderObject(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR, GameRenderer::getPositionColorProgram);
        this.renderObjects.add(quads);
        this.renderObjects.add(lines);
    }

    @Override
    public void update(MatrixStack matrices, Camera camera, float tickDelta) {
        BufferBuilderProxy linesBuf = this.lines.startBuffer();
        BufferBuilderProxy quadsBuf = this.quads.startBuffer();

        // Red box
        LineDrawing.drawBox(new Box(new BlockPos(2, 0, 0)), new Color4f(1f, 0f, 0f, 1f), camera, linesBuf);
        QuadDrawing.drawBox(new Box(new BlockPos(2, 0, 0)), new Color4f(1f, 0f, 0f, 0.1f), camera, quadsBuf);

        // Green box
        LineDrawing.drawBox(new Box(new BlockPos(0, 0, 0)), new Color4f(0f, 1f, 0f, 1f), camera, linesBuf);
        QuadDrawing.drawBox(new Box(new BlockPos(0, 0, 0)), new Color4f(0f, 1f, 0f, 0.1f), camera, quadsBuf);

        // Blue box
        LineDrawing.drawBox(new Box(new BlockPos(-2, 0, 0)), new Color4f(0f, 0f, 1f, 1f), camera, linesBuf);
        QuadDrawing.drawBox(new Box(new BlockPos(-2, 0, 0)), new Color4f(0f, 0f, 1f, 0.1f), camera, quadsBuf);

        // Blue box near world border
        LineDrawing.drawBox(new Box(new BlockPos(29000000, 0, 0)), new Color4f(0f, 0f, 1f, 1f), camera, linesBuf);
        QuadDrawing.drawBox(new Box(new BlockPos(29000000, 0, 0)), new Color4f(0f, 0f, 1f, 0.1f), camera, quadsBuf);


        LineDrawing.drawBox(29000000d, 0d, 2d, 29000001d, 1d, 3d, new Color4f(0f, 0f, 1f, 1f), camera, linesBuf);
        QuadDrawing.drawBox(29000000d, 0d, 2d, 29000001d, 1d, 3d, new Color4f(0f, 0f, 1f, 0.1f), camera, quadsBuf);


        // Line
        LineDrawing.drawLine(0, 2, 0, 1, 3, 1, new Color4f(0f, 0f, 0f, 1f), camera, linesBuf);

        // Rectangle
        LineDrawing.drawLine(5, 6, 5, 0, 6, 0, new Color4f(1f, 1f, 1f, 1f), camera, linesBuf);
        LineDrawing.drawLine(0, 6, 0, 0, 7, 0, new Color4f(1f, 1f, 1f, 1f), camera, linesBuf);
        LineDrawing.drawLine(0, 7, 0, 5, 7, 5, new Color4f(1f, 1f, 1f, 1f), camera, linesBuf);
        LineDrawing.drawLine(5, 7, 5, 5, 6, 5, new Color4f(1f, 1f, 1f, 1f), camera, linesBuf);

        // Some kind of polygon
        LineDrawing.drawLine(8, 10, 4, 0, 16, 0, new Color4f(1f, 1f, 1f, 1f), camera, linesBuf);
        LineDrawing.drawLine(0, 16, 0, 4, 13, 4, new Color4f(1f, 1f, 1f, 1f), camera, linesBuf);
        LineDrawing.drawLine(4, 13, 4, 5, 12, 5, new Color4f(1f, 1f, 1f, 1f), camera, linesBuf);
        LineDrawing.drawLine(5, 12, 5, 8, 10, 4, new Color4f(1f, 1f, 1f, 1f), camera, linesBuf);

        this.lines.endBuffer(camera);
        this.quads.endBuffer(camera);

        this.shouldUpdate = false;
    }
}
