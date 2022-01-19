package com.adryd.cauldron.api.config;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IConfigOptionNested extends IConfigOption {

    default Map<String, IConfigOption> getConfigOptionMap() {
        Map<String, IConfigOption> resultMap = new HashMap<>();
        for (IConfigOption option : getConfigOptionList()) {
            resultMap.put(option.getStorageKey(), option);
        }
        return resultMap;
    }

    List<IConfigOption> getConfigOptionList();

    @Override
    default void fromJsonObject(JsonObject object) {
        Map<String, IConfigOption> configOptionMap = getConfigOptionMap();
        for (String key : object.keySet()) {
            if (object.get(key).isJsonObject() && configOptionMap.containsKey(key)) {
                configOptionMap.get(key).fromJsonObject((JsonObject) object.get(key));
            }
        }
    }

    @Override
    default JsonObject toJsonObject() {
        JsonObject object = new JsonObject();
        List<IConfigOption> configOptionList = getConfigOptionList();
        for (IConfigOption option : configOptionList) {
            if (option.isModified()) {
                object.add(option.getStorageKey(), option.toJsonObject());
            }
        }
        return object;
    }
}
