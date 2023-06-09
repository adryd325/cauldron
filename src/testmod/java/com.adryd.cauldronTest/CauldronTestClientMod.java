package com.adryd.cauldronTest;

import com.adryd.cauldron.api.command.ClientCommandManager;
import com.adryd.cauldron.api.config.ConfigBoolean;
import com.adryd.cauldron.api.config.ConfigFile;
import com.adryd.cauldron.api.config.ConfigManager;
import com.adryd.cauldron.api.render.helper.OverlayRenderManager;
import com.adryd.cauldronTest.example.ExampleCommand;
import com.adryd.cauldronTest.example.ExampleOverlayRenderer;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CauldronTestClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Logger LOGGER = LogManager.getLogger("Cauldron Test Mod");
        ExampleCommand.register(ClientCommandManager.DISPATCHER);

        OverlayRenderManager.addRenderer(new ExampleOverlayRenderer());

        ConfigBoolean configBoolean = new ConfigBoolean("key", false);
        ConfigFile configFile = new ConfigFile("cauldron-test", true);

        configFile.addConfig(configBoolean);
        ConfigManager.addConfigFile(configFile);

        LOGGER.info(configBoolean.toJsonElement().toString());

        configBoolean.toggle();
    }

}