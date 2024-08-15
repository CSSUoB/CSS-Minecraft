package com.cssbham.cssminecraft.common.handler;

import com.cssbham.cssminecraft.common.discord.DiscordClientService;
import com.cssbham.cssminecraft.common.event.EventHandler;
import com.cssbham.cssminecraft.common.event.events.PlayerJoinEvent;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;

public class PlayerJoinEventHandler extends AbstractPlayerJoinLeaveEventHandler<PlayerJoinEvent> {

    private final DiscordClientService discordClientService;

    public PlayerJoinEventHandler(DiscordClientService discordClientService) {
        this.discordClientService = discordClientService;
    }

    @Override
    public void handle(PlayerJoinEvent event) {
        String joinMessage = String.format("__*has joined the server, %s*__", getPlayerCountMessage(event.newPlayerCount()));
        this.discordClientService.getWebHookClient().sendMessageAsMinecraftUser(event.username(), event.displayName(), joinMessage);
    }

}
