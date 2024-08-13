package com.cssbham.cssminecraft.bukkit.logger;

import com.cssbham.cssminecraft.common.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitLogger implements Logger {

    private final JavaPlugin plugin;
    private LoggingLevel serverLoggingLevel;

    public BukkitLogger(JavaPlugin plugin) {
        this.plugin = plugin;
        serverLoggingLevel = LoggingLevel.INFO;
    }

    @Override
    public LoggingLevel getServerLoggingLevel() {
        return serverLoggingLevel;
    }

    @Override
    public void setServerLoggingLevel(LoggingLevel serverLoggingLevel) {
        this.serverLoggingLevel = serverLoggingLevel;
    }

    @Override
    public void log(String str, LoggingLevel level) {
        if (serverLoggingLevel.getNumericVerbosity() < level.getNumericVerbosity()) {
            return;
        }
        switch (level) {
            case DEBUG -> plugin.getLogger().info("DEBUG: " + str);
            case INFO -> plugin.getLogger().info(str);
            case ERROR -> plugin.getLogger().severe(str);
            case WARNING -> plugin.getLogger().warning(str);
        }
    }

    @Override
    public void debug(String str) {
        log(str, LoggingLevel.DEBUG);
    }

    @Override
    public void info(String str) {
        log(str, LoggingLevel.INFO);
    }

    @Override
    public void warning(String str) {
        log(str, LoggingLevel.WARNING);
    }

    @Override
    public void severe(String str) {
        log(str, LoggingLevel.ERROR);
    }

}
