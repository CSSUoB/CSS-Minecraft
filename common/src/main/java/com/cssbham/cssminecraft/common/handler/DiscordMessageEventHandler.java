package com.cssbham.cssminecraft.common.handler;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.event.EventHandler;
import com.cssbham.cssminecraft.common.event.events.DiscordMessageEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class DiscordMessageEventHandler extends EventHandler<DiscordMessageEvent> {

    private final ServerChatAdapter serverChatAdapter;

    public DiscordMessageEventHandler(ServerChatAdapter serverChatAdapter) {
        this.serverChatAdapter = serverChatAdapter;
    }

    @Override
    public void handle(DiscordMessageEvent event) {
        serverChatAdapter.broadcastMessage(buildMessageComponent(event.sender(), event.senderColour(), event.message()));
    }

    private Component buildMessageComponent(String name, int colour, String message) {
        return Component.text("[Discord] ").color(TextColor.color(115, 138, 189))
                .append(Component.text(name).color(TextColor.color(0xFFFFFF & colour)))
                .append(Component.text(" > ").color(NamedTextColor.WHITE))
                .append(Component.text(message).color(NamedTextColor.WHITE));
    }
}
