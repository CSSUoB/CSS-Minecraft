package com.cssbham.cssminecraft.common.logger;

/**
 * Base interface for plugin logger. Implementations should
 * wrap the server or plugin specific logger.
 */
public interface Logger {

    /**
     * Get the logging level.
     *
     * @return logging level
     */
    LoggingLevel getServerLoggingLevel();

    /**
     * Set the logging level.
     *
     * @param serverLoggingLevel the new logging level
     */
    void setServerLoggingLevel(LoggingLevel serverLoggingLevel);

    /**
     * Log a message.
     *
     * @param str the message to log
     * @param level the severity of the message
     */
    void log(String str, LoggingLevel level);

    /**
     * Log a debug message.
     *
     * @param str the message to log
     */
    void debug(String str);

    /**
     * Log an informational message.
     *
     * @param str the message to log
     */
    void info(String str);

    /**
     * Log a warning message.
     *
     * @param str the message to log
     */
    void warning(String str);

    /**
     * Log an error message.
     *
     * @param str the message to log
     */
    void severe(String str);

    /**
     * Represents a numeric logging level, where lower values are more
     * severe.
     */
    enum LoggingLevel {
        ERROR(0),
        WARNING(1),
        INFO(2),
        DEBUG(3);

        private final int numericVerbosity;

        LoggingLevel(int number) {
            numericVerbosity = number;
        }

        public int getNumericVerbosity() {
            return numericVerbosity;
        }

        public static LoggingLevel fromNumber(int number) {
            for (LoggingLevel level : LoggingLevel.values()) {
                if (level.getNumericVerbosity() == number) {
                    return level;
                }
            }
            return LoggingLevel.INFO;
        }
    }
}
