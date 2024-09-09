package com.cssbham.cssminecraft.fabric.executor;

import com.cssbham.cssminecraft.common.executor.AsyncServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;
import net.minecraft.server.MinecraftServer;

public class FabricServerExecutor extends AsyncServerExecutor {

    private final MinecraftServer server;

    public FabricServerExecutor(Logger logger, MinecraftServer server) {
        super(logger);
        this.server = server;
    }

    @Override
    public void doSync(Runnable runnable) {
        server.executeSync(runnable);
    }

}
