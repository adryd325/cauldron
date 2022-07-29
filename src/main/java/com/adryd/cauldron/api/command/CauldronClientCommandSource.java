package com.adryd.cauldron.api.command;


import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CauldronClientCommandSource implements CommandSource {

    private final MinecraftClient client;
    private final ChatHud chatHud;
    private final ClientPlayerEntity player;

    public CauldronClientCommandSource(MinecraftClient client) {
        this.client = client;
        this.player = this.client.player;
        this.chatHud = this.client.inGameHud.getChatHud();
    }

    public void sendFeedback(Text message) {
        this.chatHud.addMessage(message);
    }

    public void sendError(Text message) {
        this.chatHud.addMessage(Text.literal("").append(message).formatted(Formatting.RED));
    }

    public MinecraftClient getClient() {
        return this.client;
    }

    @Override
    public Collection<String> getPlayerNames() {
        return client.getNetworkHandler().getPlayerList().stream().map(e -> e.getProfile().getName()).collect(Collectors.toList());
    }

    @Override
    public Collection<String> getTeamNames() {
        return null;
    }

    @Override
    public Collection<Identifier> getSoundIds() {
        return null;
    }

    @Override
    public Stream<Identifier> getRecipeIds() {
        return null;
    }

    @Override
    public CompletableFuture<Suggestions> getCompletions(CommandContext<?> context) {
        return null;
    }

    @Override
    public Set<RegistryKey<World>> getWorldKeys() {
        return null;
    }

    @Override
    public DynamicRegistryManager getRegistryManager() {
        return null;
    }

    @Override
    public CompletableFuture<Suggestions> listIdSuggestions(RegistryKey<? extends Registry<?>> registryRef, SuggestedIdType suggestedIdType, SuggestionsBuilder builder, CommandContext<?> context) {
        return null;
    }

    @Override
    public boolean hasPermissionLevel(int level) {
        return true;
    }

    public ClientPlayerEntity getPlayer() {
        return this.player;
    }

}
