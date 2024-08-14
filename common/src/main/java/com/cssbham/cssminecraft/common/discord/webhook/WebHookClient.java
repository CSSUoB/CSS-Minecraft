package com.cssbham.cssminecraft.common.discord.webhook;

/**
 * Abstraction for webhook clients.
 */
public interface WebHookClient {

    void initialise();

    void shutdown();

    /**
     * Send a message to the endpoint as a Minecraft player.
     *
     * @param avatarName the name of the Minecraft avatar
     * @param displayName senders display name
     * @param message message content
     */
    void sendMessageAsMinecraftUser(String avatarName, String displayName, String message);

}
