package com.cssbham.cssminecraft.forge.executor;

import com.cssbham.cssminecraft.common.executor.AsyncServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;
import net.minecraft.server.MinecraftServer;

public class ForgeServerExecutor extends AsyncServerExecutor {

    private final MinecraftServer server;

    public ForgeServerExecutor(Logger logger, MinecraftServer server) {
        super(logger);
        this.server = server;
    }

    @Override
    public void doSync(Runnable runnable) {
        server.addScheduledTask(runnable);
    }

}
