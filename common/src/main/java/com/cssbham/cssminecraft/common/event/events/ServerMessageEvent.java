package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

import java.util.UUID;

/**
 * An event which should be dispatched when a chat message is sent.
 */
public final class ServerMessageEvent implements Event {

    private final UUID sender;
    private final String username;
    private final String displayName;
    private final String message;

    public ServerMessageEvent(UUID sender, String username, String displayName, String message) {
        this.sender = sender;
        this.username = username;
        this.displayName = displayName;
        this.message = message;
    }

    public UUID getSender() {
        return sender;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMessage() {
        return message;
    }
}
