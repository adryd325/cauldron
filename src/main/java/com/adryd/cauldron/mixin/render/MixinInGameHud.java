package com.adryd.cauldron.mixin.render;

import com.adryd.cauldron.impl.render.HUDRenderInternals;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusEffectOverlay(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    private void onRenderOutlines(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        HUDRenderInternals.onHUDRender(matrices, tickDelta);
    }
}
