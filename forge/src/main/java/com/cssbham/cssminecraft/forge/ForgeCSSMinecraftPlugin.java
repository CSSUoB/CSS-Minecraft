package com.cssbham.cssminecraft.forge;

import com.cssbham.cssminecraft.forge.adapter.ForgeServerChatAdapter;
import com.cssbham.cssminecraft.forge.listener.ForgeEventAdapter;
import com.cssbham.cssminecraft.forge.logger.ForgeLogger;
import com.cssbham.cssminecraft.common.AbstractCSSMinecraftPlugin;
import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.logger.Logger;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

/**
 * Implementation of CSS Minecraft Plugin for Forge
 */
public class ForgeCSSMinecraftPlugin extends AbstractCSSMinecraftPlugin {

    public static final String MOD_ID = "cssminecraft";
    private final ForgeLogger logger;
    private ForgeServerChatAdapter serverChatAdapter;

    private MinecraftServer server;

    public ForgeCSSMinecraftPlugin() {
        this.logger = new ForgeLogger(MOD_ID);
    }

    @Override
    public void enable() {
        this.serverChatAdapter = new ForgeServerChatAdapter(server);

        super.enable();

        ForgeEventAdapter eventAdapter = new ForgeEventAdapter();
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

    public void setServer(MinecraftServer server) {
        this.server = server;
    }
}
