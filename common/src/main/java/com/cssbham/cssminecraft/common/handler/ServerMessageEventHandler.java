package com.cssbham.cssminecraft.common.handler;

import com.cssbham.cssminecraft.common.discord.DiscordClientService;
import com.cssbham.cssminecraft.common.event.Event;
import com.cssbham.cssminecraft.common.event.EventHandler;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;

public class ServerMessageEventHandler extends EventHandler<ServerMessageEvent> {

    private final DiscordClientService discordClientService;

    public ServerMessageEventHandler(DiscordClientService discordClientService) {
        this.discordClientService = discordClientService;
    }

    @Override
    public void handle(ServerMessageEvent event) {
        String sanitisedMessage = MarkdownSanitizer.sanitize(event.getMessage()).replace("@", "@\u200B");
        this.discordClientService.getWebHookClient().sendMessageAsMinecraftUser(event.getUsername(), event.getDisplayName(), sanitisedMessage);
    }

}
