package com.cssbham.cssminecraft.neoforge;

import com.cssbham.cssminecraft.common.AbstractCSSMinecraftPlugin;
import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.command.CommandService;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;
import com.cssbham.cssminecraft.neoforge.adapter.ForgeServerChatAdapter;
import com.cssbham.cssminecraft.neoforge.command.ForgeCommandService;
import com.cssbham.cssminecraft.neoforge.executor.ForgeServerExecutor;
import com.cssbham.cssminecraft.neoforge.listener.ForgeEventAdapter;
import com.cssbham.cssminecraft.neoforge.logger.ForgeLogger;
import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

/**
 * Implementation of CSS Minecraft Plugin for Forge
 */
public class NeoForgeCSSMinecraftPlugin extends AbstractCSSMinecraftPlugin {

    public static final String MOD_ID = "cssminecraft";
    private final ForgeLogger logger;
    private ForgeServerChatAdapter serverChatAdapter;

    private MinecraftServer server;
    private ForgeServerExecutor executor;
    private ForgeCommandService commandService;

    public NeoForgeCSSMinecraftPlugin() {
        this.logger = new ForgeLogger(MOD_ID);
    }

    @Override
    public void enable() {
        this.serverChatAdapter = new ForgeServerChatAdapter(server);
        this.executor = new ForgeServerExecutor(logger, server);
        this.commandService = new ForgeCommandService(logger, executor, serverChatAdapter, server);

        super.enable();

        ForgeEventAdapter eventAdapter = new ForgeEventAdapter(server, executor);
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
        return FMLPaths.CONFIGDIR.get().resolve(MOD_ID).resolve("config.yml");
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
