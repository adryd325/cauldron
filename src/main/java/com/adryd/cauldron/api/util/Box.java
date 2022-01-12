package com.adryd.cauldron.api.util;


import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Box {
    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    public Box(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public Box(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1));
    }

    public Box(BlockPos pos1, BlockPos pos2) {
        this.minX = Math.min(pos1.getX(), pos2.getX());
        this.minY = Math.min(pos1.getY(), pos2.getY());
        this.minZ = Math.min(pos1.getZ(), pos2.getZ());
        this.maxX = Math.max(pos1.getX(), pos2.getX()) + 1;
        this.maxY = Math.max(pos1.getY(), pos2.getY()) + 1;
        this.maxZ = Math.max(pos1.getZ(), pos2.getZ()) + 1;
    }

    public Box(BlockBox box) {
        this(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ());
    }

    public Box(net.minecraft.util.math.Box box) {
        this(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public Box(Vec3d pos1, Vec3d pos2) {
        this(pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z);
    }
}

