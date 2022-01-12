package com.adryd.cauldron.api.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public interface ICustomChannelHandler {
    Identifier getIdentifier();

    void onPacket(PacketByteBuf buf);
}
