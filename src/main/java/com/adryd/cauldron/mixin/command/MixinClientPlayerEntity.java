package com.adryd.cauldron.mixin.command;

import com.adryd.cauldron.impl.command.ClientCommandInternals;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity {
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onChatMessage(String message, CallbackInfo ci) {
        if (ClientCommandInternals.executeCommand(message)) {
            ci.cancel();
        }
    }
}
