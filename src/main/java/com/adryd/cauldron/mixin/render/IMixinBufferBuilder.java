package com.adryd.cauldron.mixin.render;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BufferBuilder.class)
public interface IMixinBufferBuilder {
    @Accessor("format")
    VertexFormat getFormat();
}
