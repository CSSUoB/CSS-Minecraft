package com.cssbham.cssminecraft.common;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.command.CommandService;
import com.cssbham.cssminecraft.common.command.handler.MakeGreenCommandHandler;
import com.cssbham.cssminecraft.common.config.ConfigService;
import com.cssbham.cssminecraft.common.config.option.ConfigOption;
import com.cssbham.cssminecraft.common.config.source.ConfigSource;
import com.cssbham.cssminecraft.common.config.source.YamlConfigSource;
import com.cssbham.cssminecraft.common.discord.DiscordClientService;
import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.event.SimpleEventBus;
import com.cssbham.cssminecraft.common.event.events.DiscordMessageEvent;
import com.cssbham.cssminecraft.common.event.events.PlayerJoinEvent;
import com.cssbham.cssminecraft.common.event.events.PlayerQuitEvent;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import com.cssbham.cssminecraft.common.handler.DiscordMessageEventHandler;
import com.cssbham.cssminecraft.common.handler.PlayerJoinEventHandler;
import com.cssbham.cssminecraft.common.handler.PlayerQuitEventHandler;
import com.cssbham.cssminecraft.common.handler.ServerMessageEventHandler;
import com.cssbham.cssminecraft.common.logger.Logger;
import com.cssbham.cssminecraft.common.permission.PermissionPluginService;
import com.cssbham.cssminecraft.common.permission.PermissionPluginServiceFactory;

import java.nio.file.Path;

/**
 * Abstract implementation of the CSS Minecraft plugin, to be extended by
 * platforms.
 */
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
        try {
            discordClientService.initialiseClients();
        } catch (Exception e) {
            logger.severe(String.format("Failed to initialise Discord clients: %s", e.getMessage()));
            throw e;
        }

        eventBus.subscribe(ServerMessageEvent.class, new ServerMessageEventHandler(discordClientService));
        eventBus.subscribe(PlayerJoinEvent.class, new PlayerJoinEventHandler(discordClientService));
        eventBus.subscribe(PlayerQuitEvent.class, new PlayerQuitEventHandler(discordClientService));
        eventBus.subscribe(DiscordMessageEvent.class, new DiscordMessageEventHandler(provideServerChatAdapter()));

        PermissionPluginService permissionPluginService = PermissionPluginServiceFactory.any();
        CommandService commandService = provideCommandService();

        commandService.register("makegreen", new MakeGreenCommandHandler(discordClientService, permissionPluginService), "mg", "green");
    }

    @Override
    public void disable() {
        if (null != discordClientService) {
            discordClientService.shutdownClients();
        }

        provideServerExecutor().shutdown();
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

    /**
     * Provide platform-specific {@link ServerChatAdapter}
     *
     * @return a server chat adapter wrapping platform chat functions
     */
    public abstract ServerChatAdapter provideServerChatAdapter();

    /**
     * Provide configuration path
     *
     * @return path to config file
     */
    public abstract Path provideConfigurationPath();

    /**
     * Provide platform-specific {@link ServerExecutor}
     *
     * @return a server executor wrapping the platform main thread
     */
    public abstract ServerExecutor provideServerExecutor();

    /**
     * Provide platform-specific {@link CommandService}
     *
     * @return a command service
     */
    public abstract CommandService provideCommandService();

}
