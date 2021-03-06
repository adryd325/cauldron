package com.adryd.cauldron.api.command;

import com.adryd.cauldron.impl.command.HelpCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

public class ClientCommandManager {
    public static final char COMMAND_PREFIX = '.';
    public static final CommandDispatcher<CauldronClientCommandSource> DISPATCHER = new CommandDispatcher<>();

    static {
        HelpCommand.register(DISPATCHER);
    }

    public static LiteralArgumentBuilder<CauldronClientCommandSource> literal(String literal) {
        return LiteralArgumentBuilder.literal(literal);
    }

    public static <T> RequiredArgumentBuilder<CauldronClientCommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
