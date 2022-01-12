package com.adryd.cauldron.impl.network;

import com.adryd.cauldron.api.network.ICustomChannelHandler;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class CustomChannelInternals {
    private static final HashMap<Identifier, ICustomChannelHandler> channelHandlers = new HashMap<>();

    /**
     * NOT A PUBLIC METHOD
     */
    public static boolean onPacket(CustomPayloadS2CPacket packet) {
        Identifier channel = packet.getChannel();
        ICustomChannelHandler handler = channelHandlers.get(channel);
        if (handler != null) {
            handler.onPacket(packet.getData());
            return true;
        }
        return false;
    }

    public static void registerChannel(ICustomChannelHandler channelHandler) {
        channelHandlers.put(channelHandler.getIdentifier(), channelHandler);
    }

    public static void unregisterChannel(ICustomChannelHandler channelHandler) {
        channelHandlers.remove(channelHandler.getIdentifier(), channelHandler);
    }

}
