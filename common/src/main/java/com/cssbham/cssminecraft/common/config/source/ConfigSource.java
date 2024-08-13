package com.cssbham.cssminecraft.common.config.source;

public interface ConfigSource {

    int getInteger(String path, int def);

    long getLong(String path, long def);

    boolean getBoolean(String path, boolean def);

    String getString(String path, String def);

    void initialise();

}
