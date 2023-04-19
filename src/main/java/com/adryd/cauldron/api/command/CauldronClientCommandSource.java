package com.adryd.cauldron.api.command;


import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.command.CommandSource;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;
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
    public Collection<String> getTeamNames() {
        return null;
    }

    @Override
    public Stream<Identifier> getSoundIds() {
        return Registries.SOUND_EVENT.stream().map(SoundEvent::getId);
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
        return this.client.getNetworkHandler().getWorldKeys();
    }

    @Override
    public DynamicRegistryManager getRegistryManager() {
        return this.client.getNetworkHandler().getRegistryManager();
    }

    @Override
    public FeatureSet getEnabledFeatures() {
        return this.client.getNetworkHandler().getEnabledFeatures();
    }

    @Override
    public boolean hasPermissionLevel(int level) {
        return true;
    }

    public ClientPlayerEntity getPlayer() {
        return this.player;
    }

    private static String format(double d) {
        return String.format(Locale.ROOT, "%.2f", d);
    }

    private static String format(int i) {
        return Integer.toString(i);
    }

    // Vanilla code from ClientCommandManager
    @Override
    public Collection<CommandSource.RelativePosition> getBlockPositionSuggestions() {
        HitResult hitResult = this.client.crosshairTarget;
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            return Collections.singleton(new CommandSource.RelativePosition(format(blockPos.getX()), format(blockPos.getY()), format(blockPos.getZ())));
        } else {
            return CommandSource.super.getBlockPositionSuggestions();
        }
    }

    // Vanilla code from ClientCommandManager
    @Override
    public Collection<CommandSource.RelativePosition> getPositionSuggestions() {
        HitResult hitResult = this.client.crosshairTarget;
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
            Vec3d vec3d = hitResult.getPos();
            return Collections.singleton(new CommandSource.RelativePosition(format(vec3d.x), format(vec3d.y), format(vec3d.z)));
        } else {
            return CommandSource.super.getPositionSuggestions();
        }
    }

    // Vanilla code from ClientCommandManager
    @Override
    public CompletableFuture<Suggestions> listIdSuggestions(RegistryKey<? extends Registry<?>> registryRef, CommandSource.SuggestedIdType suggestedIdType, SuggestionsBuilder builder, CommandContext<?> context) {
        return (CompletableFuture<Suggestions>) this.getRegistryManager().getOptional(registryRef).map(registry -> {
            this.suggestIdentifiers(registry, suggestedIdType, builder);
            return builder.buildFuture();
        }).orElseGet(() -> this.getCompletions(context));
    }

    // Vanilla code from ClientCommandManager
    @Override
    public Collection<String> getPlayerNames() {
        List<String> list = Lists.newArrayList();

        for(PlayerListEntry playerListEntry : this.client.getNetworkHandler().getPlayerList()) {
            list.add(playerListEntry.getProfile().getName());
        }

        return list;
    }

    // Vanilla code from ClientCommandManager
    @Override
    public Collection<String> getEntitySuggestions() {
        return (Collection<String>)(this.client.crosshairTarget != null && this.client.crosshairTarget.getType() == HitResult.Type.ENTITY
                ? Collections.singleton(((EntityHitResult)this.client.crosshairTarget).getEntity().getUuidAsString())
                : Collections.emptyList());
    }

    // Just return player names since we don't want to notify the server of client commands
    @Override
    public Collection<String> getChatSuggestions() {
        return this.getPlayerNames();
    }
}
