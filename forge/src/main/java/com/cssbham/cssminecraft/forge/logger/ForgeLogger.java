package com.cssbham.cssminecraft.forge.logger;

import com.cssbham.cssminecraft.common.logger.AbstractLogger;
import org.apache.logging.log4j.LogManager;

public class ForgeLogger extends AbstractLogger {

    private final org.apache.logging.log4j.Logger logger;

    public ForgeLogger(String name) {
        this.logger = LogManager.getLogger(name);
    }

    @Override
    protected void logInfo(String string) {
        logger.info(string);
    }

    @Override
    protected void logError(String string) {
        logger.error(string);
    }

    @Override
    protected void logWarning(String string) {
        logger.warn(string);
    }

}
