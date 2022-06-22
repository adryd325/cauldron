package com.adryd.cauldron.mixin.command;

import com.adryd.cauldron.impl.command.ClientCommandInternals;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity {
    @Inject(method = "sendChatMessage(Ljava/lang/String;Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true)
    public void onSendChatMessage(String message, Text preview, CallbackInfo ci) {
        if (ClientCommandInternals.executeCommand(message, (ClientPlayerEntity) ((Object) this), preview)) {
            ci.cancel();
        }
    }
}
