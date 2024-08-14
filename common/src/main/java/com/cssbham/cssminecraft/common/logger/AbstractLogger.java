package com.cssbham.cssminecraft.common.logger;

/**
 * Abstract implementation of logger, implementing the server logging
 * level logic. Platform-specific implementations should wrap the server
 * logger.
 */
public abstract class AbstractLogger implements Logger {

    private Logger.LoggingLevel serverLoggingLevel = LoggingLevel.INFO;

    @Override
    public Logger.LoggingLevel getServerLoggingLevel() {
        return serverLoggingLevel;
    }

    @Override
    public void setServerLoggingLevel(Logger.LoggingLevel serverLoggingLevel) {
        this.serverLoggingLevel = serverLoggingLevel;
    }

    @Override
    public void log(String str, Logger.LoggingLevel level) {
        if (serverLoggingLevel.getNumericVerbosity() < level.getNumericVerbosity()) {
            return;
        }
        switch (level) {
            case DEBUG -> logInfo("DEBUG: " + str);
            case INFO -> logInfo(str);
            case ERROR -> logError(str);
            case WARNING -> logWarning(str);
        }
    }

    @Override
    public void debug(String str) {
        log(str, Logger.LoggingLevel.DEBUG);
    }

    @Override
    public void info(String str) {
        log(str, Logger.LoggingLevel.INFO);
    }

    @Override
    public void warning(String str) {
        log(str, Logger.LoggingLevel.WARNING);
    }

    @Override
    public void severe(String str) {
        log(str, Logger.LoggingLevel.ERROR);
    }

    protected abstract void logInfo(String string);

    protected abstract void logError(String string);

    protected abstract void logWarning(String string);
}
