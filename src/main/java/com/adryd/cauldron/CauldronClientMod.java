package com.adryd.cauldron;

import com.adryd.cauldron.impl.render.helpers.OverlayRenderInternals;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class CauldronClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(OverlayRenderInternals::afterEntities);
    }
}
