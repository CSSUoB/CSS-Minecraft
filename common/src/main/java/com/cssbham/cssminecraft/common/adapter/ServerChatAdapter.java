package com.cssbham.cssminecraft.common.adapter;

import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * Abstraction for platform-specific server chat functions.
 */
public interface ServerChatAdapter {

    /**
     * Broadcast a message to every player online and the server
     * console.
     *
     * @param message the message to broadcast
     */
    void broadcastMessage(Component message);

    /**
     * Send a message to a specific player.
     *
     * @param user the user to send to
     * @param component the message to send
     */
    void sendMessageToPlayer(UUID user, Component component);

    /**
     * Send a message to console.
     *
     * @param component the message to send
     */
    void sendMessageToConsole(Component component);

}
