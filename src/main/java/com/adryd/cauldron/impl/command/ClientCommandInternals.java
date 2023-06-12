package com.adryd.cauldron.impl.command;

import com.adryd.cauldron.api.command.CauldronClientCommandSource;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

import static com.adryd.cauldron.api.command.ClientCommandManager.COMMAND_PREFIX;
import static com.adryd.cauldron.api.command.ClientCommandManager.DISPATCHER;

public class ClientCommandInternals {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    private static boolean hasCommands() {
        // if more than just the help command is registered
        return (DISPATCHER.getRoot().getChildren().size() > 1);
    }

    public static void execute(String command, CauldronClientCommandSource source) {
        StringReader stringReader = new StringReader(command);
        if (stringReader.canRead() && stringReader.peek() == COMMAND_PREFIX) {
            stringReader.skip();
        }
        try {
            DISPATCHER.execute(stringReader, source);
        } catch (CommandException commandException) {
            source.sendError(commandException.getTextMessage());
        } catch (CommandSyntaxException commandException) {
            source.sendError(Texts.toText(commandException.getRawMessage()));
            if (commandException.getInput() != null && commandException.getCursor() >= 0) {
                int position = Math.min(commandException.getInput().length(), commandException.getCursor());
                MutableText mutableText = Text.literal("").formatted(Formatting.GRAY).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)));
                if (position > 10) {
                    mutableText.append("...");
                }
                mutableText.append(commandException.getInput().substring(Math.max(0, position - 10), position));
                if (position < commandException.getInput().length()) {
                    MutableText text = Text.literal(commandException.getInput().substring(position)).formatted(Formatting.RED, Formatting.UNDERLINE);
                    mutableText.append(text);
                }
                mutableText.append(Text.translatable("command.context.here").formatted(Formatting.RED, Formatting.ITALIC));
                source.sendError(mutableText);
            }
        }
    }

    // Used by MixinCommandSuggestor
    public static String executeCommand(String message) {
        if (!hasCommands()) return message;
        // allow people to send "./" often used to show people how to use a server command
        if (message.startsWith(Character.toString(COMMAND_PREFIX)) && !message.startsWith(COMMAND_PREFIX + "/")) {
            if (message.startsWith(COMMAND_PREFIX + Character.toString(COMMAND_PREFIX))) {
                // allow people to send "." or messages prefixed with "." in chat, sometimes used to check if someone's cheating or something
                String newMessage = message.substring(1);
                return newMessage;
            }
            execute(message, new CauldronClientCommandSource(client));
            return "";
        }
        return message;
    }

    // Used by MixinChatPreviewRequestor
    public static String chatPreviewMutator(String message) {
        if (!hasCommands()) return message;
        // allow people to send "./" often used to show people how to use a server command
        if (message.startsWith(Character.toString(COMMAND_PREFIX)) && !message.startsWith(COMMAND_PREFIX + "/")) {
            if (message.startsWith(COMMAND_PREFIX + Character.toString(COMMAND_PREFIX))) {
                // allow people to send "." or messages prefixed with "." in chat, sometimes used to check if someone's cheating or something
                return message.substring(1);
            }
            return "";
        }
        return message;
    }

    // Used by MixinCommandSuggestor
    public static boolean shouldShowSuggestions(String message) {
        if (!hasCommands() || message.startsWith(Character.toString(COMMAND_PREFIX) + COMMAND_PREFIX) || message.startsWith(COMMAND_PREFIX + "/"))
            return false;
        return message.startsWith(Character.toString(COMMAND_PREFIX));
    }

    // Used by MixinCommandSuggestor
    // Copied from vanilla
    public static RootCommandNode<CommandSource> getCommandTree() {
        HashMap<CommandNode<CauldronClientCommandSource>, CommandNode<CommandSource>> map = Maps.newHashMap();
        RootCommandNode<CommandSource> rootCommandNode = new RootCommandNode<>();
        map.put(DISPATCHER.getRoot(), rootCommandNode);
        createCommandTree(DISPATCHER.getRoot(), rootCommandNode, new CauldronClientCommandSource(client), map);
        return rootCommandNode;
    }

    // Copied from vanilla
    private static void createCommandTree(CommandNode<CauldronClientCommandSource> tree, CommandNode<CommandSource> result, CauldronClientCommandSource source, Map<CommandNode<CauldronClientCommandSource>, CommandNode<CommandSource>> resultNodes) {
        for (CommandNode<CauldronClientCommandSource> commandNode : tree.getChildren()) {
            RequiredArgumentBuilder requiredArgumentBuilder;
            if (!commandNode.canUse(source)) continue;
            ArgumentBuilder<CommandSource, ?> argumentBuilder = (ArgumentBuilder)  commandNode.createBuilder();
            argumentBuilder.requires(source2 -> true);
            if (argumentBuilder.getCommand() != null) {
                argumentBuilder.executes(context -> 0);
            }
            if (argumentBuilder instanceof RequiredArgumentBuilder && (requiredArgumentBuilder = (RequiredArgumentBuilder)argumentBuilder).getSuggestionsProvider() != null) {
                requiredArgumentBuilder.suggests(SuggestionProviders.getLocalProvider(requiredArgumentBuilder.getSuggestionsProvider()));
            }
            if (argumentBuilder.getRedirect() != null) {
                argumentBuilder.redirect(resultNodes.get(argumentBuilder.getRedirect()));
            }
            CommandNode<CommandSource> requiredArgumentBuilder2 = argumentBuilder.build();
            resultNodes.put(commandNode, requiredArgumentBuilder2);
            result.addChild(requiredArgumentBuilder2);
            if (commandNode.getChildren().isEmpty()) continue;
            createCommandTree(commandNode, requiredArgumentBuilder2, source, resultNodes);
        }
    }
}
