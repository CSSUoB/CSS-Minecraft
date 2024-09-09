package com.cssbham.cssminecraft.common.config.source;

/**
 * Abstraction for configuration sources.
 */
public interface ConfigSource {

    int getInteger(String path, int def);

    long getLong(String path, long def);

    boolean getBoolean(String path, boolean def);

    String getString(String path, String def);

    /**
     * Initialise this configuration source and create default
     * configuration.
     */
    void initialise();

}
