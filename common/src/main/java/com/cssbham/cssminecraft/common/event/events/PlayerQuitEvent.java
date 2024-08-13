package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

import java.util.UUID;

public record PlayerQuitEvent(UUID sender, String username, String displayName, int newPlayerCount) implements Event {

}
