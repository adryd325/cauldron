package com.adryd.cauldron.mixin.render;

import com.adryd.cauldron.impl.render.WorldRenderInternals;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {

    @Inject(method = "render", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/client/render/WorldRenderer;renderWeather(Lnet/minecraft/client/render/LightmapTextureManager;FDDD)V"))
    private void renderWorldLast(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        WorldRenderInternals.onWorldRender(matrices, positionMatrix, camera, tickDelta);
    }

    @Inject(method = "render", slice = @Slice(from = @At(value = "FIELD", ordinal = 1, target = "Lnet/minecraft/client/render/RenderPhase;WEATHER_TARGET:Lnet/minecraft/client/render/RenderPhase$Target;"), to = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/client/render/WorldRenderer;renderWeather(Lnet/minecraft/client/render/LightmapTextureManager;FDDD)V")), at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/ShaderEffect;render(F)V"))
    private void renderWorldLastFabulous(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        WorldRenderInternals.onWorldRender(matrices, positionMatrix, camera, tickDelta);
    }

}
