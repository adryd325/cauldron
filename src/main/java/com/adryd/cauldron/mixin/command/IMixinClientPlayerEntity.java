package com.adryd.cauldron.mixin.command;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.message.ChatMessageSigner;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientPlayerEntity.class)
public interface IMixinClientPlayerEntity {
    @Invoker("sendChatMessagePacket")
    void invokeSendChatMessagePacket(ChatMessageSigner signer, String message, @Nullable Text preview);
}
