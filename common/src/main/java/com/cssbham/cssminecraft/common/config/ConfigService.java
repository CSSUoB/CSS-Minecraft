package com.cssbham.cssminecraft.common.config;

import com.cssbham.cssminecraft.common.config.option.ConfigValue;
import com.cssbham.cssminecraft.common.config.source.ConfigSource;
import com.cssbham.cssminecraft.common.logger.Logger;

import java.util.Objects;

/**
 * Configuration service to create plugin config, and retrieve config
 * values.
 */
public class ConfigService {

    private final Logger logger;

    private ConfigSource configSource;

    public ConfigService(Logger logger) {
        this.logger = logger;
    }

    /**
     * Initialise a config source and associate it with this service.
     *
     * @param configSource the config source
     */
    public void useSource(ConfigSource configSource) {
        this.configSource = configSource;
        this.logger.info(String.format("Using config source: %s", configSource.getClass().getName()));

        configSource.initialise();
    }

    /**
     * Get the value of a {@link ConfigValue}.
     *
     * @param option the option to retrieve
     * @return the value
     * @param <T> the type of config value
     */
    public <T> T getValue(ConfigValue<T> option) {
        Objects.requireNonNull(configSource, "config source not initialised");

        return option.get(configSource);
    }

}
