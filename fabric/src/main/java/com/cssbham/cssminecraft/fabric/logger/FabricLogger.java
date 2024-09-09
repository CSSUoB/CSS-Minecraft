package com.cssbham.cssminecraft.fabric.logger;

import com.cssbham.cssminecraft.common.logger.AbstractLogger;
import org.slf4j.LoggerFactory;

public class FabricLogger extends AbstractLogger {

    private final org.slf4j.Logger logger;

    public FabricLogger(String name) {
        this.logger = LoggerFactory.getLogger(name);
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
