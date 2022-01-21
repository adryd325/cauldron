package com.adryd.cauldron;

import com.adryd.cauldron.impl.config.ConfigManagerInternals;
import com.adryd.cauldron.impl.render.helpers.OverlayRenderInternals;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CauldronClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Render
        WorldRenderEvents.AFTER_ENTITIES.register(OverlayRenderInternals::afterEntities);

        // Config
        ClientLifecycleEvents.CLIENT_STOPPING.register((client) -> {
            ConfigManagerInternals.writeAll();
        });

        ServerLifecycleEvents.SERVER_STOPPING.register((client) -> {
            ConfigManagerInternals.writeAll();
        });
    }
}
