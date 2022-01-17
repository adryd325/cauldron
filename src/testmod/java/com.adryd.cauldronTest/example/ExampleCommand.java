package com.adryd.cauldronTest.example;

import com.adryd.cauldron.api.command.CauldronClientCommandSource;
import com.adryd.cauldron.api.command.ClientCommandManager;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.text.LiteralText;

public class ExampleCommand {
    public static void register(CommandDispatcher<CauldronClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("example").executes(context -> execute(context.getSource())));
    }

    private static int execute(CauldronClientCommandSource source) {
        source.sendFeedback(new LiteralText("Hello from Cauldron test mod!"));
        return 1;
    }
}