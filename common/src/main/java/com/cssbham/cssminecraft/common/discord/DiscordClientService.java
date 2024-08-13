package com.cssbham.cssminecraft.common.discord;

import com.cssbham.cssminecraft.common.config.ConfigService;
import com.cssbham.cssminecraft.common.config.option.ConfigOption;
import com.cssbham.cssminecraft.common.discord.client.DiscordClient;
import com.cssbham.cssminecraft.common.discord.client.JDADiscordClient;
import com.cssbham.cssminecraft.common.discord.webhook.DiscordWebHookClient;
import com.cssbham.cssminecraft.common.discord.webhook.WebHookClient;
import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.logger.Logger;

import java.util.Objects;

public class DiscordClientService {

    private final ConfigService configService;
    private final EventBus eventBus;
    private final Logger logger;

    private DiscordClient discordClient;
    private WebHookClient webHookClient;

    public DiscordClientService(ConfigService configService, EventBus eventBus, Logger logger) {
        this.configService = configService;
        this.eventBus = eventBus;
        this.logger = logger;
    }

    public void initialiseClients() {
        this.discordClient = new JDADiscordClient(
                eventBus,
                configService.getValue(ConfigOption.BOT_TOKEN),
                configService.getValue(ConfigOption.DISCORD_SERVER_ID),
                configService.getValue(ConfigOption.MEMBER_ROLE_ID),
                configService.getValue(ConfigOption.BRIDGE_CHANNEL_ID)
        );
        this.webHookClient = new DiscordWebHookClient(
                configService.getValue(ConfigOption.WEBHOOK_URL),
                configService.getValue(ConfigOption.AVATAR_SERVICE)
        );

        this.logger.info("Initialising Discord clients");

        this.discordClient.initialise();
        this.webHookClient.initialise();
    }

    public DiscordClient getDiscordClient() {
        Objects.requireNonNull(discordClient, "discord client not initialised");

        return discordClient;
    }

    public WebHookClient getWebHookClient() {
        Objects.requireNonNull(webHookClient, "webhook client not initialised");

        return webHookClient;
    }
}
