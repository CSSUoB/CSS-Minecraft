package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

import java.util.UUID;

/**
 * An event which should be dispatched when a discord message is received.
 * This event SHOULD be dispatched async.
 */
public class DiscordMessageEvent implements Event {

    private final String sender;
    private final String message;
    private final int senderColour;

    public DiscordMessageEvent(String sender, String message, int senderColour) {
        this.sender = sender;
        this.message = message;
        this.senderColour = senderColour;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public int getSenderColour() {
        return senderColour;
    }
}
