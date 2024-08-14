package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

/**
 * An event which should be dispatched when a discord message is received.
 * This event SHOULD be dispatched async.
 *
 * @param sender name of sender
 * @param message message content
 * @param senderColour the senders role colour on discord
 */
public record DiscordMessageEvent(String sender, String message, int senderColour) implements Event {

}
