package com.adryd.cauldronTest;

import com.adryd.cauldron.api.command.ClientCommandManager;
import com.adryd.cauldron.api.render.helper.OverlayRenderManager;
import com.adryd.cauldronTest.example.ExampleCommand;
import com.adryd.cauldronTest.example.ExampleOverlayRenderer;
import net.fabricmc.api.ClientModInitializer;

public class CauldronTestClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ExampleCommand.register(ClientCommandManager.DISPATCHER);

        OverlayRenderManager.addRenderer(new ExampleOverlayRenderer());
    }

}