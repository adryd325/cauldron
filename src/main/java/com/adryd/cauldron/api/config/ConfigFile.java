package com.adryd.cauldron.api.config;

import com.adryd.cauldron.CauldronReference;
import com.google.gson.*;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConfigFile {
    private static final File configDir = new File(MinecraftClient.getInstance().runDirectory, "config");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File file;
    private final List<IConfigOption> configOptions;
    private final boolean keepRoot;
    private boolean loaded;
    private JsonObject root;


    public ConfigFile(String id) {
        this(id, false);
    }

    public ConfigFile(String id, boolean keepRoot) {
        this.loaded = false;
        this.configOptions = new ArrayList<>();
        this.file = new File(configDir, id + ".json");
        this.keepRoot = keepRoot;
        this.read();
    }

    public void read() {
        if (!this.loaded && this.file.exists() && this.file.isFile() && this.file.canRead()) {
            String fileName = this.file.getAbsolutePath();
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8)) {
                JsonElement element = JsonParser.parseReader(reader);
                if (element != null && element.isJsonObject()) {
                    this.root = (JsonObject) element;
                    this.loaded = true;
                    for (IConfigOption option : configOptions) {
                        option.fromJsonElement(root.get(option.getKey()));
                    }
                }
            } catch (Exception e) {
                CauldronReference.LOGGER.error("Failed to parse JSON file \"{}\"", fileName, e);
            }
        } else {
            CauldronReference.LOGGER.warn("Tried to re-read config file \"{}\"", this.file.getAbsolutePath());
        }
    }

    public void write() {
        JsonObject root = new JsonObject();
        if (this.loaded && this.keepRoot) {
            root = this.root;
        }
        for (IConfigOption option : configOptions) {
            root.add(option.getKey(), option.toJsonElement());
        }
        File tmpFile = new File(this.file.getParentFile(), this.file.getName() + ".tmp");

        if (tmpFile.exists()) {
            tmpFile = new File(this.file.getParentFile(), this.file.getName() + "." + UUID.randomUUID() + ".tmp");
        }

        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tmpFile), StandardCharsets.UTF_8);
            writer.write(GSON.toJson(root));
            writer.close();

            if (this.file.exists() && this.file.isFile() && !this.file.delete()) {
                CauldronReference.LOGGER.error("Failed to delete file \"{}\"", this.file.getAbsolutePath());
            } else {
                tmpFile.renameTo(file);
            }
        } catch (Exception e) {
            CauldronReference.LOGGER.error("Failed to write JSON to file \"{}\"", tmpFile.getAbsolutePath(), e);
        }
    }

    public void addConfig(IConfigOption option) {
        configOptions.add(option);
        if (this.loaded) {
            option.fromJsonElement(root.get(option.getKey()));
        }
    }

    public void addConfigs(List<IConfigOption> options) {
        configOptions.addAll(options);
        if (this.loaded) {
            for (IConfigOption option : options) {
                option.fromJsonElement(root.get(option.getKey()));
            }
        }
    }
}
