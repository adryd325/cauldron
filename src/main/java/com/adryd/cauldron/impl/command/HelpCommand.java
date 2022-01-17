package com.adryd.cauldron.impl.command;

import com.adryd.cauldron.api.command.CauldronClientCommandSource;
import com.adryd.cauldron.api.command.ClientCommandManager;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.Map;

public class HelpCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.help.failed"));

    // Copied from vanilla
    public static void register(CommandDispatcher<CauldronClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager
                .literal("help")
                .executes(ctx -> {
                    Map<CommandNode<CauldronClientCommandSource>, String> map = dispatcher.getSmartUsage(dispatcher.getRoot(), ctx.getSource());

                    for (String string : map.values()) {
                        ctx.getSource().sendFeedback(new LiteralText(ClientCommandManager.COMMAND_PREFIX + string));
                    }

                    return map.size();
                }).then(ClientCommandManager.argument("command", StringArgumentType.greedyString()).executes((ctx) -> {
                    ParseResults<CauldronClientCommandSource> parseResults = dispatcher.parse(StringArgumentType.getString(ctx, "command"), ctx.getSource());
                    if (parseResults.getContext().getNodes().isEmpty()) {
                        throw FAILED_EXCEPTION.create();
                    } else {
                        Map<CommandNode<CauldronClientCommandSource>, String> map = dispatcher.getSmartUsage((Iterables.getLast(parseResults.getContext().getNodes())).getNode(), ctx.getSource());

                        for (String string : map.values()) {
                            CauldronClientCommandSource source = ctx.getSource();
                            source.sendFeedback(new LiteralText(ClientCommandManager.COMMAND_PREFIX + parseResults.getReader().getString() + " " + string));
                        }

                        return map.size();
                    }
                }))
        );
    }
}