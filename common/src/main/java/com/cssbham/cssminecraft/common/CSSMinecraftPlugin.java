package com.cssbham.cssminecraft.common;

import com.cssbham.cssminecraft.common.logger.Logger;

/**
 * Base interface for the CSS Minecraft plugin.
 */
public interface CSSMinecraftPlugin {

    /**
     * Enable the plugin. Should be called during the enable
     * phase of the plugin lifecycle.
     */
    void enable();

    /**
     * Disable the plugin. Should be called during the disable
     * phase of the plugin lifecycle, or during server shutdown.
     */
    void disable();

    /**
     * Get the plugin logger
     */
    Logger getLogger();
}
