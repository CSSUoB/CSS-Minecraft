package com.cssbham.cssminecraft.common.executor;

import com.cssbham.cssminecraft.common.logger.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Abstract implementation of server executor with a thread pool for
 * async tasks. Subclasses should implement the sync method around the
 * server scheduler.
 */
public abstract class AsyncServerExecutor implements ServerExecutor {

    private final ThreadPoolExecutor executor;
    private final Logger logger;

    public AsyncServerExecutor(Logger logger) {
        this.executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.logger = logger;
    }

    @Override
    public void doAsync(Runnable runnable) {
        executor.submit(runnable);
    }

    @Override
    public void shutdown() {
        this.executor.shutdown();
        try {
            if (!this.executor.awaitTermination(30, TimeUnit.SECONDS)) {
                logger.severe("Async executor timed out while awaiting shutdown!");
            }
        } catch (InterruptedException e) {
            logger.warning("Interrupted while awaiting async executor termination");
            e.printStackTrace();
        }
    }

}
