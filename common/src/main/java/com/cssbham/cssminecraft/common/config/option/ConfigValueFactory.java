package com.cssbham.cssminecraft.common.config.option;

public final class ConfigValueFactory {

    private ConfigValueFactory() {}

    public static ConfigValue<String> buildString(String path, String def) {
        return new ConfigValue<>(path, def, (configSource -> configSource.getString(path, def)));
    }

    public static ConfigValue<Long> buildLong(String path, long def) {
        return new ConfigValue<>(path, def, (configSource -> configSource.getLong(path, def)));
    }

}
