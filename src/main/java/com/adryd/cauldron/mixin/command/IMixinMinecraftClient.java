package com.adryd.cauldron.mixin.command;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface IMixinMinecraftClient {
    @Invoker("openChatScreen")
    void invokeOpenChatScreen(String text);
}
