package com.cssbham.cssminecraft.bukkit;

import com.cssbham.cssminecraft.bukkit.adapter.BukkitServerChatAdapter;
import com.cssbham.cssminecraft.bukkit.listener.BukkitEventListener;
import com.cssbham.cssminecraft.bukkit.logger.BukkitLogger;
import com.cssbham.cssminecraft.common.AbstractCSSMinecraftPlugin;
import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of CSS Minecraft Plugin for Bukkit
 */
public class BukkitCSSMinecraftPlugin extends AbstractCSSMinecraftPlugin {

    private final JavaPlugin plugin;
    private final BukkitLogger bukkitLogger;
    private final BukkitServerChatAdapter serverChatAdapter;

    public BukkitCSSMinecraftPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        this.bukkitLogger = new BukkitLogger(plugin);
        this.serverChatAdapter = new BukkitServerChatAdapter();
    }

    @Override
    public void enable() {
        super.enable();

        BukkitEventListener eventListener = new BukkitEventListener(plugin);
        eventListener.bindPlatformToEventBus(super.getEventBus());
    }

    @Override
    public Logger getLogger() {
        return bukkitLogger;
    }

    @Override
    public ServerChatAdapter provideServerChatAdapter() {
        return serverChatAdapter;
    }

    @Override
    public Path provideConfigurationPath() {
        return Paths.get(plugin.getDataFolder().getPath(), "config.yml");
    }
}
