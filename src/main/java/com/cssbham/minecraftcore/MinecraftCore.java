package com.cssbham.minecraftcore;

import com.cssbham.minecraftcore.commands.CommandMakeGreen;
import com.cssbham.minecraftcore.discord.TeXBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftCore extends JavaPlugin implements Listener {

    private TeXBot teXBot;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onEnable() {
        saveDefaultConfig();
        if (teXBot != null) {
            teXBot.shutdown();
        }
        try {
            teXBot = new TeXBot(this);
        } catch (Exception e) {
            return;
        }

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("makegreen").setExecutor(new CommandMakeGreen(teXBot));
        this.getLogger().info("Plugin has been enabled.");

    }

    @Override
    public void onDisable() {
        this.getLogger().warning("Plugin has been disabled.");
        try {
            teXBot.shutdown();
            teXBot = null;
        } catch (Exception ignored) {
        }
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        teXBot.sendSanitisedMessageToDiscord(event.getPlayer(), event.getMessage());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        teXBot.sendMessageToDiscord(event.getPlayer(), "__*has joined the server*__");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        teXBot.sendMessageToDiscord(event.getPlayer(), "__*has left the server*__");
    }
}
