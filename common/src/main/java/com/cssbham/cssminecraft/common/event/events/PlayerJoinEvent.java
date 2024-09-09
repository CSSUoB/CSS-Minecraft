package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

import java.util.UUID;

/**
 * An event which should be dispatched when a player joins.
 *
 * @param sender UUID of joining player
 * @param username username of joining player
 * @param displayName display name of joining player
 * @param newPlayerCount new player count
 */
public record PlayerJoinEvent(UUID sender, String username, String displayName, int newPlayerCount) implements Event {

}
