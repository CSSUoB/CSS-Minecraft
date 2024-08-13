package com.cssbham.cssminecraft.common.handler;

import com.cssbham.cssminecraft.common.discord.DiscordClientService;
import com.cssbham.cssminecraft.common.event.EventHandler;
import com.cssbham.cssminecraft.common.event.events.PlayerJoinEvent;
import com.cssbham.cssminecraft.common.event.events.PlayerQuitEvent;

public class PlayerQuitEventHandler extends EventHandler<PlayerQuitEvent> {

    private final DiscordClientService discordClientService;

    public PlayerQuitEventHandler(DiscordClientService discordClientService) {
        this.discordClientService = discordClientService;
    }

    @Override
    public void handle(PlayerQuitEvent event) {
        String joinMessage = String.format("__*has left the server, there are now %d players online*__",
                event.newPlayerCount());
        this.discordClientService.getWebHookClient().sendMessageAsMinecraftUser(event.username(), event.displayName(), joinMessage);
    }

}
