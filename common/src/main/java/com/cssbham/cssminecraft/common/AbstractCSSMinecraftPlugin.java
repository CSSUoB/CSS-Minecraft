package com.cssbham.cssminecraft.common;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.config.ConfigService;
import com.cssbham.cssminecraft.common.config.option.ConfigOption;
import com.cssbham.cssminecraft.common.config.source.ConfigSource;
import com.cssbham.cssminecraft.common.config.source.StubConfigSource;
import com.cssbham.cssminecraft.common.config.source.YamlConfigSource;
import com.cssbham.cssminecraft.common.discord.DiscordClientService;
import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.event.SimpleEventBus;
import com.cssbham.cssminecraft.common.event.events.DiscordMessageEvent;
import com.cssbham.cssminecraft.common.event.events.PlayerJoinEvent;
import com.cssbham.cssminecraft.common.event.events.PlayerQuitEvent;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;
import com.cssbham.cssminecraft.common.handler.DiscordMessageEventHandler;
import com.cssbham.cssminecraft.common.handler.PlayerJoinEventHandler;
import com.cssbham.cssminecraft.common.handler.PlayerQuitEventHandler;
import com.cssbham.cssminecraft.common.handler.ServerMessageEventHandler;
import com.cssbham.cssminecraft.common.logger.Logger;

import java.nio.file.Path;

public abstract class AbstractCSSMinecraftPlugin implements CSSMinecraftPlugin {

    private ConfigService configService;
    private DiscordClientService discordClientService;
    private EventBus eventBus;

    @Override
    public void enable() {
        ConfigSource configSource = new YamlConfigSource(provideConfigurationPath());

        Logger logger = getLogger();

        this.configService = new ConfigService(logger);
        configService.useSource(configSource);

        logger.setServerLoggingLevel(Logger.LoggingLevel.fromNumber(configService.getValue(ConfigOption.LOGGING_LEVEL)));

        this.eventBus = new SimpleEventBus(logger);

        this.discordClientService = new DiscordClientService(configService, eventBus, logger);
        discordClientService.initialiseClients();

        eventBus.subscribe(ServerMessageEvent.class, new ServerMessageEventHandler(discordClientService));
        eventBus.subscribe(PlayerJoinEvent.class, new PlayerJoinEventHandler(discordClientService));
        eventBus.subscribe(PlayerQuitEvent.class, new PlayerQuitEventHandler(discordClientService));
        eventBus.subscribe(DiscordMessageEvent.class, new DiscordMessageEventHandler(provideServerChatAdapter()));
    }

    @Override
    public void disable() {
        if (null != discordClientService) {
            discordClientService.shutdownClients();
        }
    }

    public ConfigService getConfigService() {
        return configService;
    }

    public DiscordClientService getDiscordClientService() {
        return discordClientService;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public abstract ServerChatAdapter provideServerChatAdapter();

    public abstract Path provideConfigurationPath();

}
