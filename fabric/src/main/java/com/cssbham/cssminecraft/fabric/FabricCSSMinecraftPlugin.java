package com.cssbham.cssminecraft.fabric;

import com.cssbham.cssminecraft.common.command.CommandService;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import com.cssbham.cssminecraft.fabric.adapter.FabricServerChatAdapter;
import com.cssbham.cssminecraft.fabric.command.FabricCommandService;
import com.cssbham.cssminecraft.fabric.executor.FabricServerExecutor;
import com.cssbham.cssminecraft.fabric.listener.FabricEventAdapter;
import com.cssbham.cssminecraft.fabric.logger.FabricLogger;
import com.cssbham.cssminecraft.common.AbstractCSSMinecraftPlugin;
import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.logger.Logger;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;

/**
 * Implementation of CSS Minecraft Plugin for Fabric
 */
public class FabricCSSMinecraftPlugin extends AbstractCSSMinecraftPlugin {

    public static final String MOD_ID = "cssminecraft";
    private final FabricLogger logger;
    private FabricServerChatAdapter serverChatAdapter;
    private FabricServerExecutor executor;
    private FabricCommandService commandService;

    private MinecraftServer server;

    public FabricCSSMinecraftPlugin() {
        this.logger = new FabricLogger(MOD_ID);
    }

    @Override
    public void enable() {
        this.serverChatAdapter = new FabricServerChatAdapter(server);
        this.executor = new FabricServerExecutor(logger, server);
        this.commandService = new FabricCommandService(logger, executor, serverChatAdapter, server);

        super.enable();

        FabricEventAdapter eventAdapter = new FabricEventAdapter(executor);
        eventAdapter.bindPlatformToEventBus(super.getEventBus());
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public ServerChatAdapter provideServerChatAdapter() {
        return serverChatAdapter;
    }

    @Override
    public Path provideConfigurationPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(MOD_ID).resolve("config.yml");
    }

    @Override
    public ServerExecutor provideServerExecutor() {
        return executor;
    }

    @Override
    public CommandService provideCommandService() {
        return commandService;
    }

    public void setServer(MinecraftServer server) {
        this.server = server;
    }
}
