package com.adryd.cauldron.api.render.util;

import com.adryd.cauldron.api.util.Color4f;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class LineDrawing {

    public static void drawBox(Box box, Color4f color, BufferBuilder buffer) {
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;
        drawBox(minX, minY, minZ, maxX, maxY, maxZ, color, buffer);
    }

    public static void drawBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f color, BufferBuilder buffer) {
        // West side
        drawLine(minX, minY, minZ, minX, minY, maxZ, color, buffer);
        drawLine(minX, minY, maxZ, minX, maxY, maxZ, color, buffer);
        drawLine(minX, maxY, maxZ, minX, maxY, minZ, color, buffer);
        drawLine(minX, maxY, minZ, minX, minY, minZ, color, buffer);

        // East side
        drawLine(maxX, minY, maxZ, maxX, minY, minZ, color, buffer);
        drawLine(maxX, minY, minZ, maxX, maxY, minZ, color, buffer);
        drawLine(maxX, maxY, minZ, maxX, maxY, maxZ, color, buffer);
        drawLine(maxX, maxY, maxZ, maxX, minY, maxZ, color, buffer);

        // North side
        drawLine(maxX, minY, minZ, minX, minY, minZ, color, buffer);
        drawLine(minX, maxY, minZ, maxX, maxY, minZ, color, buffer);

        // South side
        drawLine(minX, minY, maxZ, maxX, minY, maxZ, color, buffer);
        drawLine(maxX, maxY, maxZ, minX, maxY, maxZ, color, buffer);
    }

    public static void drawLine(double startX, double startY, double startZ, double endX, double endY, double endZ, Color4f color, BufferBuilder buffer) {
        float lenX = (float) (endX - startX);
        float lenY = (float) (endY - startY);
        float lenZ = (float) (endZ - startZ);
        float distance = (float) Math.sqrt(lenX * lenX + lenY * lenY + lenZ * lenZ);
        lenX /= distance;
        lenY /= distance;
        lenZ /= distance;

        buffer.vertex(startX, startY, startZ).color(color.r, color.g, color.b, color.a).normal(lenX, lenY, lenZ).next();
        buffer.vertex(endX, endY, endZ).color(color.r, color.g, color.b, color.a).normal(lenX, lenY, lenZ).next();
    }
}
