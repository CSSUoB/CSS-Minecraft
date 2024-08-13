package com.cssbham.cssminecraft.common.config;

import com.cssbham.cssminecraft.common.config.option.ConfigValue;
import com.cssbham.cssminecraft.common.config.source.ConfigSource;
import com.cssbham.cssminecraft.common.logger.Logger;

import java.util.Objects;

public class ConfigService {

    private final Logger logger;

    private ConfigSource configSource;

    public ConfigService(Logger logger) {
        this.logger = logger;
    }

    public void useSource(ConfigSource configSource) {
        this.configSource = configSource;
        this.logger.info(String.format("Using config source: %s", configSource.getClass().getName()));

        configSource.initialise();
    }

    public <T> T getValue(ConfigValue<T> option) {
        Objects.requireNonNull(configSource, "config source not initialised");

        return option.get(configSource);
    }

}
