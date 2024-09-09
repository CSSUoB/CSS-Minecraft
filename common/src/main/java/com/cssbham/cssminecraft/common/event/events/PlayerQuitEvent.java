package com.cssbham.cssminecraft.common.event.events;

import com.cssbham.cssminecraft.common.event.Event;

import java.util.UUID;

/**
 * An event which should be dispatched when a player quits.
 *
 * @param sender UUID of leaving player
 * @param username username of leaving player
 * @param displayName display name of leaving player
 * @param newPlayerCount new player count
 */
public record PlayerQuitEvent(UUID sender, String username, String displayName, int newPlayerCount) implements Event {

}
