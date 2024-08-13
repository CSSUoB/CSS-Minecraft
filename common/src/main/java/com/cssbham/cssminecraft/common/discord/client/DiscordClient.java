package com.cssbham.cssminecraft.common.discord.client;

import com.cssbham.cssminecraft.common.event.EventHandler;

public interface DiscordClient {

    void initialise();

    void shutdown();

    boolean isMember(String identifier);

}
