package com.adryd.cauldron.api.config;

import com.adryd.cauldron.CauldronReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IConfigOptionNested extends IConfigOption {
    default Map<String, IConfigOption> getConfigOptionMap() {
        Map<String, IConfigOption> resultMap = new HashMap<>();
        for (IConfigOption option : getConfigOptionList()) {
            resultMap.put(option.getKey(), option);
        }
        return resultMap;
    }

    List<IConfigOption> getConfigOptionList();

    @Override
    default boolean isModified() {
        for (IConfigOption option : getConfigOptionList()) {
            if (option.isModified()) {
                return true;
            }
        }
        return false;
    }

    @Override
    default void resetToDefault() {
        for (IConfigOption option : getConfigOptionList()) {
            option.resetToDefault();
        }
    }


    @Override
    default void fromJsonElement(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            Map<String, IConfigOption> configOptionMap = getConfigOptionMap();
            for (String key : object.keySet()) {
                if (object.get(key).isJsonObject() && configOptionMap.containsKey(key)) {
                    configOptionMap.get(key).fromJsonElement(object.get(key));
                }
            }
        } else {
            CauldronReference.LOGGER.warn("Failed to read storage key \"{}\" as object", this.getKey());
        }
    }

    @Override
    default JsonElement toJsonElement() {
        JsonObject object = new JsonObject();
        for (IConfigOption option : getConfigOptionList()) {
            if (option.isModified()) {
                object.add(option.getKey(), option.toJsonElement());
            }
        }
        return object;
    }
}
