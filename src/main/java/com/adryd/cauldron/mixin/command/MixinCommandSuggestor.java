package com.adryd.cauldron.mixin.command;

import com.adryd.cauldron.impl.command.ClientCommandInternals;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CommandSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.CompletableFuture;

@Mixin(CommandSuggestor.class)
public abstract class MixinCommandSuggestor {
    @Final
    @Shadow
    MinecraftClient client;

    @Shadow
    CommandSuggestor.SuggestionWindow window;

    @Shadow
    boolean completingSuggestions;

    @Final
    @Shadow
    TextFieldWidget textField;

    @Shadow
    private ParseResults<CommandSource> parse;

    @Shadow
    private CompletableFuture<Suggestions> pendingSuggestions;

    @Shadow
    protected abstract void show();

    @Inject(method = "refresh", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z", remap = false), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)

    private void commandSuggestions(CallbackInfo ci, String string, StringReader stringReader) {
        if (stringReader.canRead(string.length())
                && ClientCommandInternals.shouldShowSuggestions(stringReader.getString())
                && this.client.player != null) {
            stringReader.setCursor(stringReader.getCursor() + 1);
            CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>(ClientCommandInternals.getCommandTree());
            if (this.parse == null) {
                this.parse = dispatcher.parse(stringReader, client.getNetworkHandler().getCommandSource());
            }
            if (textField.getCursor() >= 1 && (this.window == null || !this.completingSuggestions)) {
                this.pendingSuggestions = dispatcher.getCompletionSuggestions(this.parse, textField.getCursor());
                this.pendingSuggestions.thenRun(() -> {
                    if (this.pendingSuggestions.isDone()) {
                        this.show();
                    }
                });
            }
            ci.cancel();
        }
    }
}
