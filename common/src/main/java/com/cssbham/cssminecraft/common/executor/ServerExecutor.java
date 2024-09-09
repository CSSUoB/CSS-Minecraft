package com.cssbham.cssminecraft.common.executor;

/**
 * An executor which can interface with the server main thread.
 */
public interface ServerExecutor {

    /**
     * Run synchronously on main thread
     *
     * @param runnable task to run
     */
    void doSync(Runnable runnable);

    /**
     * Run asynchronously outside the main thread
     *
     * @param runnable task tp run
     */
    void doAsync(Runnable runnable);

    /**
     * Shut down thread pool for asynchronous tasks
     */
    void shutdown();

}
