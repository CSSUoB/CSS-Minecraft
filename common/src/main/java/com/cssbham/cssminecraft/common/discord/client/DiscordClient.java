package com.cssbham.cssminecraft.common.discord.client;

/**
 * Abstraction for Discord clients.
 */
public interface DiscordClient {

    void initialise();

    void shutdown();

    /**
     * Get whether a discord tag has the Member role
     *
     * @param identifier discord tag
     * @return true if they have the role, false otherwise
     */
    boolean isMember(String identifier);

}
