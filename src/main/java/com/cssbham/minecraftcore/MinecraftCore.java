package com.cssbham.minecraftcore;

import com.cssbham.minecraftcore.commands.CommandMakeGreen;
import com.cssbham.minecraftcore.discord.DiscordBridge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftCore extends JavaPlugin implements Listener {

    private DiscordBridge discordBridge;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onEnable() {
        saveDefaultConfig();
        if (discordBridge != null) {
            discordBridge.shutdown();
        }
        try {
            discordBridge = new DiscordBridge(this);
        } catch (Exception e) {
            return;
        }

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("makegreen").setExecutor(new CommandMakeGreen(discordBridge));
        this.getLogger().info("Plugin has been enabled.");

    }

    @Override
    public void onDisable() {
        this.getLogger().warning("Plugin has been disabled.");
        try {
            discordBridge.shutdown();
            discordBridge = null;
        } catch (Exception ignored) {
        }
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        discordBridge.sendSanitisedMessageToDiscord(event.getPlayer(), event.getMessage());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        discordBridge.sendMessageToDiscord(event.getPlayer(), "__*has joined the server*__");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        discordBridge.sendMessageToDiscord(event.getPlayer(), "__*has left the server*__");
    }
}
