package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

public record DiscordMessageEvent(String sender, String message, int senderColour) implements Event {

}
