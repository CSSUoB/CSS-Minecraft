package com.cssbham.cssminecraft.common.config.option;

import com.cssbham.cssminecraft.common.config.source.ConfigSource;

import java.util.function.Function;

/**
 * Abstraction for config values.
 *
 * @param <T> the type
 */
public class ConfigValue<T> {

    private final String path;
    private final T def;
    private final Function<? super ConfigSource, T> getter;

    public ConfigValue(String path, T def, Function<? super ConfigSource, T> getter) {
        this.path = path;
        this.def = def;
        this.getter = getter;
    }

    /**
     * Get this value from the config source.
     *
     * @param configSource the config source
     * @return the value
     */
    public T get(ConfigSource configSource) {
        return this.getter.apply(configSource);
    }

    /**
     * Get the path for this value.
     *
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * Get the default value for this value.
     *
     * @return the default value
     */
    public T getDefault() {
        return def;
    }
}
