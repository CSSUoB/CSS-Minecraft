package com.cssbham.minecraftcore;

import com.cssbham.minecraftcore.commands.CommandMakeGreen;
import com.cssbham.minecraftcore.discord.DiscordBridge;
import org.bukkit.Server;
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
        discordBridge.sendMessageToDiscord(event.getPlayer(),
                "**has joined the server, "  + getOnlineMessage(event.getPlayer().getServer(), false) + "**");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        discordBridge.sendMessageToDiscord(event.getPlayer(),
                "**has left the server, " + getOnlineMessage(event.getPlayer().getServer(), true) + "**");
    }

    private String getOnlineMessage(Server server, boolean leaving) {
        int amount = server.getOnlinePlayers().size();
        if (leaving) {
            amount--;
        }
        // This shouldn't? happen.
        if (amount < 0) {
            amount = 0;
        }
        switch (amount) {
            case 0: {
                return "there are now no players online.";
            }
            case 1: {
                return "there is now 1 player online.";
            }
            default: {
                return "there are now " + amount + " players online.";
            }
        }
    }
}
