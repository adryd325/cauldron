package com.adryd.cauldron.api.config;

import com.adryd.cauldron.CauldronReference;
import com.adryd.cauldron.api.keybind.KeybindAction;
import com.adryd.cauldron.api.keybind.KeybindContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Set;
import java.util.function.Consumer;

public class ConfigKeybind extends ConfigOptionBase<ConfigKeybind> implements IConfigOption {
    private final KeybindAction defaultAction;
    private final KeybindContext defaultContext;
    private final String defaultKeyBind;
    private final boolean defaultCancelVanilla;
    private final boolean defaultOrderSensitive;


    private KeybindAction action;
    private KeybindContext context;
    private String keyBind;
    private boolean cancelVanilla;
    private boolean orderSensitive;

    private Set<Consumer<Boolean>> callbacks;

    public ConfigKeybind(String configKey, String name, String keyBind, KeybindAction action, KeybindContext context, boolean cancelVanilla, boolean orderSensitive) {
        super(configKey, name);
        this.action = this.defaultAction = action;
        this.context = this.defaultContext = context;
        this.keyBind = this.defaultKeyBind = keyBind;
        this.cancelVanilla = this.defaultCancelVanilla = cancelVanilla;
        this.orderSensitive = this.defaultOrderSensitive = orderSensitive;
        this.callbacks = Set.of();
    }

    @Override
    public JsonElement toJsonElement() {
        JsonObject obj = new JsonObject();
        obj.addProperty("action", this.action.toString());
        obj.addProperty("context", this.context.toString());
        obj.addProperty("cancelVanilla", this.keyBind);
        obj.addProperty("keyBind", this.keyBind);
        obj.addProperty("cancelVanilla", this.cancelVanilla);
        obj.addProperty("orderSensitive", this.orderSensitive);
        return obj;
    }

    @Override
    public void fromJsonElement(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = (JsonObject) element;

            // Action
            if (object.has("action") &&
                    object.get("action").isJsonPrimitive() &&
                    object.getAsJsonPrimitive("action").isString()) {
                this.action = KeybindAction.valueOf(object.get("action").getAsString());
            } else {
                this.action = this.defaultAction;
            }

            // Context
            if (object.has("context") &&
                    object.get("context").isJsonPrimitive() &&
                    object.getAsJsonPrimitive("context").isString()) {
                this.context = KeybindContext.valueOf(object.get("context").getAsString());
            } else {
                this.context = this.defaultContext;
            }

            // KeyBind
            if (object.has("keyBind") &&
                    object.get("keyBind").isJsonPrimitive() &&
                    object.getAsJsonPrimitive("keyBind").isString()) {
                this.keyBind = object.getAsJsonPrimitive("keyBind").getAsString();
            } else {
                this.keyBind = this.defaultKeyBind;
            }

            // CancelVanilla
            if (object.has("cancelVanilla") &&
                    object.get("cancelVanilla").isJsonPrimitive() &&
                    object.getAsJsonPrimitive("cancelVanilla").isBoolean()) {
                this.cancelVanilla = object.getAsJsonPrimitive("cancelVanilla").getAsBoolean();

            } else {
                this.cancelVanilla = this.defaultCancelVanilla;
            }

            // OrderSensitive
            if (object.has("orderSensitive") &&
                    object.get("orderSensitive").isJsonPrimitive() &&
                    object.getAsJsonPrimitive("orderSensitive").isBoolean()) {
                this.orderSensitive = object.getAsJsonPrimitive("orderSensitive").getAsBoolean();

            } else {
                this.orderSensitive = this.defaultOrderSensitive;
            }

        } else {
            CauldronReference.LOGGER.warn("Failed to read storage key \"{}\" as boolean", this.getKey());
        }
    }

    @Override
    public boolean isModified() {
        return this.action != this.defaultAction ||
                this.context != this.defaultContext ||
                this.orderSensitive != this.defaultOrderSensitive ||
                this.cancelVanilla != this.defaultCancelVanilla ||
                !this.keyBind.equals(this.defaultKeyBind);
    }

    @Override
    public void resetToDefault() {
        this.action = this.defaultAction;
        this.context = this.defaultContext;
        this.keyBind = this.defaultKeyBind;
        this.orderSensitive = this.defaultOrderSensitive;
        this.cancelVanilla = this.defaultCancelVanilla;
    }

    public KeybindAction getAction() {
        return action;
    }

    public String getKeyBind() {
        return keyBind;
    }

    public KeybindContext getContext() {
        return context;
    }

    public boolean isOrderSensitive() {
        return orderSensitive;
    }

    public boolean isCancelVanilla() {
        return cancelVanilla;
    }

    public void addCallback(Consumer<Boolean> method) {
        this.callbacks.add(method);
    }
}