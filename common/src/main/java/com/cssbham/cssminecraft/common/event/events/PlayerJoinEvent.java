package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

import java.util.UUID;

/**
 * An event which should be dispatched when a player joins.
 */
public final class PlayerJoinEvent implements Event {

    private final String sender;
    private final String username;
    private final String displayName;
    private final int newPlayerCount;

    public PlayerJoinEvent(String sender, String username, String displayName, int newPlayerCount) {
        this.sender = sender;
        this.username = username;
        this.displayName = displayName;
        this.newPlayerCount = newPlayerCount;
    }

    public String getSender() {
        return sender;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getNewPlayerCount() {
        return newPlayerCount;
    }
}
