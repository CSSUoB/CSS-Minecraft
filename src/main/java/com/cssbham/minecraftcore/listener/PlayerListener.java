package com.cssbham.minecraftcore.listener;

import com.cssbham.minecraftcore.MinecraftCore;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Represents a player listener.
 * Used to listen to player events.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        MinecraftCore.getDiscordBridge()
                .sendSanitisedMessageToDiscord(event.getPlayer(), event.getMessage());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MinecraftCore.getDiscordBridge()
                .sendMessageToDiscord(
                        event.getPlayer(),
                "__*has joined the server, "  + getOnlineMessage(event.getPlayer().getServer(), false) + "*__"
                );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        MinecraftCore.getDiscordBridge()
                .sendMessageToDiscord(
                        event.getPlayer(),
                "__*has left the server, " + getOnlineMessage(event.getPlayer().getServer(), true) + "*__"
                );
    }

    /**
     * Used to get the online message.
     *
     * @param server The instance of the server. If there are multiple servers.
     * @param leaving True if the player is leaving the server.
     * @return The message to send.
     */
    private String getOnlineMessage(Server server, boolean leaving) {
        int amount = server.getOnlinePlayers().size();
        if (leaving) amount--;

        // If the number of players on the serve already returned 0.
        if (amount < 0) amount = 0;

        // Return message.
        return switch (amount) {
            case 0 -> "there are now no players online.";
            case 1 -> "there is now 1 player online.";
            default -> "there are now " + amount + " players online.";
        };
    }
}