package com.cssbham.cssminecraft.bukkit;

import com.cssbham.cssminecraft.bukkit.adapter.BukkitServerChatAdapter;
import com.cssbham.cssminecraft.bukkit.command.BukkitCommandService;
import com.cssbham.cssminecraft.bukkit.executor.BukkitServerExecutor;
import com.cssbham.cssminecraft.bukkit.listener.BukkitEventListener;
import com.cssbham.cssminecraft.bukkit.logger.BukkitLogger;
import com.cssbham.cssminecraft.common.AbstractCSSMinecraftPlugin;
import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.command.CommandSender;
import com.cssbham.cssminecraft.common.command.CommandService;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of CSS Minecraft Plugin for Bukkit
 */
public class BukkitCSSMinecraftPlugin extends AbstractCSSMinecraftPlugin {

    private final JavaPlugin plugin;
    private final BukkitLogger logger;
    private final BukkitServerChatAdapter serverChatAdapter;
    private final BukkitServerExecutor executor;
    private final BukkitCommandService commandService;

    public BukkitCSSMinecraftPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = new BukkitLogger(plugin);
        this.serverChatAdapter = new BukkitServerChatAdapter();
        this.executor = new BukkitServerExecutor(logger, plugin);
        this.commandService = new BukkitCommandService(logger, executor, serverChatAdapter);
    }

    @Override
    public void enable() {
        super.enable();

        BukkitEventListener eventListener = new BukkitEventListener(plugin);
        eventListener.bindPlatformToEventBus(super.getEventBus());

        plugin.getCommand("makegreen").setExecutor(commandService);
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
        return Paths.get(plugin.getDataFolder().getPath(), "config.yml");
    }

    @Override
    public ServerExecutor provideServerExecutor() {
        return executor;
    }

    @Override
    public CommandService provideCommandService() {
        return commandService;
    }
}
