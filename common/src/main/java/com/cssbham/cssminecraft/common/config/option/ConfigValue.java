package com.cssbham.cssminecraft.common.config.option;

import com.cssbham.cssminecraft.common.config.source.ConfigSource;

import java.util.function.Function;

public class ConfigValue<T> {

    private final String path;
    private final T def;
    private final Function<? super ConfigSource, T> getter;

    public ConfigValue(String path, T def, Function<? super ConfigSource, T> getter) {
        this.path = path;
        this.def = def;
        this.getter = getter;
    }

    public T get(ConfigSource configSource) {
        return this.getter.apply(configSource);
    }

    public String getPath() {
        return path;
    }

    public T getDefault() {
        return def;
    }
}
