package com.cssbham.cssminecraft.common.command;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * A wrapper for command senders.
 */
public class CommandSender {

   private final UUID uuid;
   private final String name;
   private final boolean console;
   private final ServerChatAdapter chatAdapter;

    public CommandSender(ServerChatAdapter chatAdapter, UUID uuid, String name, boolean console) {
        this.uuid = uuid;
        this.name = name;
        this.console = console;
        this.chatAdapter = chatAdapter;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean isConsole() {
        return console;
    }

    public void sendMessage(Component message) {
        if (console) {
            chatAdapter.sendMessageToConsole(message);
        } else {
            chatAdapter.sendMessageToPlayer(uuid, message);
        }
    }
}
