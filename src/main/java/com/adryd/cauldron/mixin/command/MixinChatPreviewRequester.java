package com.adryd.cauldron.mixin.command;

import com.adryd.cauldron.impl.command.ClientCommandInternals;
import net.minecraft.client.network.ChatPreviewRequester;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatPreviewRequester.class)
public class MixinChatPreviewRequester {
    @Inject(method = "tryRequest", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ChatPreviewRequester;shouldRequest(J)Z"), cancellable = true)
    private void cancelIfIsHandledByCauldron(String message, long currentTime, CallbackInfoReturnable<Boolean> cir) {
        if (message.equals("")) {
            cir.setReturnValue(false);
        }
    }

    @ModifyVariable(method = "tryRequest", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private String mutateMessage(String message) {
        return ClientCommandInternals.chatPreviewMutator(message);
    }
}
