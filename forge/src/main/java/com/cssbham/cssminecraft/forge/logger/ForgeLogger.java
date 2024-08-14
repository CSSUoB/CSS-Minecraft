package com.cssbham.cssminecraft.forge.logger;

import com.cssbham.cssminecraft.common.logger.Logger;
import org.apache.logging.log4j.LogManager;

public class ForgeLogger implements Logger {

    private final org.apache.logging.log4j.Logger logger;
    private LoggingLevel serverLoggingLevel;

    public ForgeLogger(String name) {
        this.logger = LogManager.getLogger(name);
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
            case DEBUG -> logger.info("DEBUG: " + str);
            case INFO -> logger.info(str);
            case ERROR -> logger.error(str);
            case WARNING -> logger.warn(str);
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
