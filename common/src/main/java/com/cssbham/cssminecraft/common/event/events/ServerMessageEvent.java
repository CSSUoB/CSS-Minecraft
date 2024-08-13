package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

import java.util.UUID;

public record ServerMessageEvent(UUID sender, String username, String displayName, String message) implements Event {

}
