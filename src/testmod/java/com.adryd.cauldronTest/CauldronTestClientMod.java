package com.adryd.cauldronTest;

import com.adryd.cauldron.api.command.ClientCommandManager;
import com.adryd.cauldron.api.render.HUDRenderManager;
import com.adryd.cauldron.api.render.WorldRenderManager;
import com.adryd.cauldron.api.render.helper.OverlayRenderManager;
import com.adryd.cauldronTest.example.ExampleCommand;
import com.adryd.cauldronTest.example.ExampleHUDRenderer;
import com.adryd.cauldronTest.example.ExampleOverlayRenderer;
import com.adryd.cauldronTest.example.ExampleWorldRenderer;
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