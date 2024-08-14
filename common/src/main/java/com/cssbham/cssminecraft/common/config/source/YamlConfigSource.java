package com.cssbham.cssminecraft.common.config.source;

import com.cssbham.cssminecraft.common.config.option.ConfigOption;
import com.cssbham.cssminecraft.common.config.option.ConfigValue;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Config source implementation for SnakeYAML.
 */
public class YamlConfigSource implements ConfigSource {

    private final Path configurationPath;

    private Map<String, Object> data;

    public YamlConfigSource(Path configurationPath) {
        this.configurationPath = configurationPath;
    }

    @Override
    public void initialise() {
        try {
            this.createDefaultIfNotExists();

            try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(configurationPath))) {
                Yaml yaml = new Yaml();
                this.data = yaml.load(reader);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDefaultIfNotExists() throws IOException {
        if (Files.exists(configurationPath)) {
            return;
        }

        Files.createDirectories(configurationPath.getParent());

        Map<String, Object> configuration = new HashMap<>();
        for (ConfigValue<?> configValue : ConfigOption.allValues()) {
            configuration.put(configValue.getPath(), configValue.getDefault());
        }
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);

        try (OutputStreamWriter writer = new OutputStreamWriter(
                Files.newOutputStream(configurationPath, StandardOpenOption.CREATE)
        )) {
            yaml.dump(configuration, writer);
        }
    }

    @Override
    public int getInteger(String path, int def) {
        if (null == data) return def;
        Object val = data.getOrDefault(path, def);
        if (!(val instanceof Number)) return def;

        return ((Number) val).intValue();
    }

    @Override
    public long getLong(String path, long def) {
        if (null == data) return def;
        Object val = data.getOrDefault(path, def);
        if (!(val instanceof Number)) return def;

        return ((Number) val).longValue();
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        if (null == data) return def;
        Object val = data.getOrDefault(path, def);
        if (!(val instanceof Boolean)) return def;

        return (boolean) val;
    }

    @Override
    public String getString(String path, String def) {
        if (null == data) return def;

        return String.valueOf(data.getOrDefault(path, def));
    }


}
