package com.adryd.cauldron.mixin.network;

import com.adryd.cauldron.impl.network.CustomChannelInternals;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler {
    @Inject(method = "onCustomPayload", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/CustomPayloadS2CPacket;getChannel()Lnet/minecraft/util/Identifier;"), cancellable = true)
    private void onCustomPayload(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        if (CustomChannelInternals.onPacket(packet)) {
            ci.cancel();
        }
    }
}
