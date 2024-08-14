package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

import java.util.UUID;

/**
 * An event which should be dispatched when a chat message is sent.
 *
 * @param sender UUID of sender
 * @param username username of sender
 * @param displayName display name of sender
 * @param message message content
 */
public record ServerMessageEvent(UUID sender, String username, String displayName, String message) implements Event {

}
