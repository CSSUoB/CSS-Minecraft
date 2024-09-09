package com.cssbham.cssminecraft.fabric.listener;

import com.cssbham.cssminecraft.common.event.Event;
import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.event.PlatformEventAdapter;
import com.cssbham.cssminecraft.common.event.events.PlayerJoinEvent;
import com.cssbham.cssminecraft.common.event.events.PlayerQuitEvent;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class FabricEventAdapter implements PlatformEventAdapter {

    private final ServerExecutor executor;

    public FabricEventAdapter(ServerExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void bindPlatformToEventBus(EventBus eventBus) {
        ServerMessageEvents.CHAT_MESSAGE.register((message, player, parameters) -> {
            String name = player.getName().getString();

            dispatchEvent(eventBus, new ServerMessageEvent(
                    player.getUuid(),
                    player.getName().getString(),
                    (null == player.getDisplayName()) ? name : player.getDisplayName().getString(),
                    message.getSignedContent()
            ));
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            String name = player.getName().getString();

            dispatchEvent(eventBus, new PlayerJoinEvent(
                    player.getUuid(),
                    player.getName().getString(),
                    (null == player.getDisplayName()) ? name : player.getDisplayName().getString(),
                    server.getCurrentPlayerCount() + 1
            ));
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler,  server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            String name = player.getName().getString();

            dispatchEvent(eventBus, new PlayerQuitEvent(
                    player.getUuid(),
                    player.getName().getString(),
                    (null == player.getDisplayName()) ? name : player.getDisplayName().getString(),
                    server.getCurrentPlayerCount() - 1
            ));
        });
    }

    private void dispatchEvent(EventBus eventBus, Event event) {
        executor.doAsync(() -> eventBus.dispatch(event));
    }
}
