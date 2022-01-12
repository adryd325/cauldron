package com.adryd.cauldron.api.render.helper;

import com.adryd.cauldron.api.util.Color4f;
import com.adryd.cauldron.mixin.render.IMixinBufferBuilder;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class BufferBuilderProxy extends BufferBuilder {
    public static final int MAX_BUFFER_SIZE = 2097152;

    @Nullable
    private VertexFormat vertexFormat;

    private double posX;
    private double posY;
    private double posZ;
    private float colorR;
    private float colorG;
    private float colorB;
    private float colorA;
    private float normalX;
    private float normalY;
    private float normalZ;

    public BufferBuilderProxy(int initialCapacity) {
        super(initialCapacity);
    }

    public BufferBuilderProxy vertex(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        return this;
    }

    public BufferBuilderProxy vertex(Vec3d pos) {
        this.posX = pos.x;
        this.posY = pos.y;
        this.posZ = pos.z;
        return this;
    }

    public BufferBuilderProxy color(float r, float g, float b, float a) {
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        this.colorA = a;
        return this;
    }

    public BufferBuilderProxy color(Color4f color) {
        this.colorR = color.r;
        this.colorG = color.g;
        this.colorB = color.b;
        this.colorA = color.a;
        return this;
    }

    public BufferBuilderProxy normal(float x, float y, float z) {
        this.normalX = x;
        this.normalY = y;
        this.normalZ = z;
        return this;
    }

    public void begin(VertexFormat.DrawMode drawMode, VertexFormat vertexFormat) {
        super.begin(drawMode, vertexFormat);
        this.vertexFormat = vertexFormat;
    }

    public void next() {
        for (VertexFormatElement element : ((IMixinBufferBuilder) this).getFormat().getElements()) {
            switch (element.getType()) {
                case POSITION -> super.vertex(this.posX, this.posY, this.posZ);
                case COLOR -> super.color(this.colorR, this.colorG, this.colorB, this.colorA);
                case NORMAL -> super.normal(this.normalX, this.normalY, this.normalZ);
            }
        }
        super.next();
    }
}
