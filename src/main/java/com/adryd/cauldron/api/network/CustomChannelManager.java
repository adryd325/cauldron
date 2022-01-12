package com.adryd.cauldron.api.network;

import com.adryd.cauldron.impl.network.CustomChannelInternals;

public class CustomChannelManager {
    public static void registerChannel(ICustomChannelHandler channelHandler) {
        CustomChannelInternals.registerChannel(channelHandler);
    }

    public static void unregisterChannel(ICustomChannelHandler channelHandler) {
        CustomChannelInternals.unregisterChannel(channelHandler);
    }
}
