package com.adryd.cauldron.example;

import com.adryd.cauldron.api.command.ClientCommandManager;
import com.adryd.cauldron.api.command.ClientCommandSource;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.text.LiteralText;

public class ExampleCommand {
    public static void register(CommandDispatcher<ClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("example").executes(context -> execute(context.getSource())));
    }

    private static int execute(ClientCommandSource source) {
        source.sendFeedback(new LiteralText("Hello from libunnamed! <3"));
        return 1;
    }
}