package com.cssbham.cssminecraft.common.handler;

import com.cssbham.cssminecraft.common.discord.DiscordClientService;
import com.cssbham.cssminecraft.common.event.Event;
import com.cssbham.cssminecraft.common.event.EventHandler;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;

public class ServerMessageEventHandler extends EventHandler<ServerMessageEvent> {

    private final DiscordClientService discordClientService;

    public ServerMessageEventHandler(DiscordClientService discordClientService) {
        this.discordClientService = discordClientService;
    }

    @Override
    public void handle(ServerMessageEvent event) {
        //TODO sanitise
        this.discordClientService.getWebHookClient().sendMessageAsMinecraftUser(event.username(), event.displayName(), event.message());
    }

}
