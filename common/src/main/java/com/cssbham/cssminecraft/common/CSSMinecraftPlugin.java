package com.cssbham.cssminecraft.common;

import com.cssbham.cssminecraft.common.logger.Logger;

public interface CSSMinecraftPlugin {

    void enable();

    void disable();

    Logger getLogger();
}
