package com.cssbham.cssminecraft.bukkit.logger;

import com.cssbham.cssminecraft.common.logger.AbstractLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitLogger extends AbstractLogger {

    private final JavaPlugin plugin;

    public BukkitLogger(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void logInfo(String string) {
        plugin.getLogger().info(string);
    }

    @Override
    protected void logError(String string) {
        plugin.getLogger().severe(string);
    }

    @Override
    protected void logWarning(String string) {
        plugin.getLogger().warning(string);
    }

}
