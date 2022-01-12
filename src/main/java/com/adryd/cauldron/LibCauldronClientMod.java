package com.adryd.cauldron;

import com.adryd.cauldron.api.command.ClientCommandManager;
import com.adryd.cauldron.api.render.HUDRenderManager;
import com.adryd.cauldron.api.render.WorldRenderManager;
import com.adryd.cauldron.api.render.helper.OverlayRenderManager;
import com.adryd.cauldron.example.ExampleCommand;
import com.adryd.cauldron.example.ExampleHUDRenderer;
import com.adryd.cauldron.example.ExampleOverlayRenderer;
import com.adryd.cauldron.example.ExampleWorldRenderer;
import net.fabricmc.api.ClientModInitializer;

public class LibCauldronClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ExampleCommand.register(ClientCommandManager.DISPATCHER);

        HUDRenderManager.addHUDRenderer(new ExampleHUDRenderer());

        WorldRenderManager.addWorldRenderer(new ExampleWorldRenderer());

        OverlayRenderManager overlayRenderManager = new OverlayRenderManager();
        WorldRenderManager.addWorldRenderer(overlayRenderManager);
        overlayRenderManager.addRenderer(new ExampleOverlayRenderer());

    }
}
