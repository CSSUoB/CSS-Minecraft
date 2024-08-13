package com.cssbham.cssminecraft.common.config.source;

public class StubConfigSource implements ConfigSource {

    @Override
    public int getInteger(String path, int def) {
        return 0;
    }

    @Override
    public long getLong(String path, long def) {
        return 0;
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return false;
    }

    @Override
    public String getString(String path, String def) {
        return "test";
    }

    @Override
    public void initialise() {

    }

}
