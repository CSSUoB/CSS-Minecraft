package com.cssbham.cssminecraft.common.discord.webhook;

import com.cssbham.cssminecraft.common.event.EventHandler;

public interface WebHookClient {

    void initialise();

    void sendMessageAsMinecraftUser(String avatarName, String displayName, String message);

}
