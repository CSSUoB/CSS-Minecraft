package com.cssbham.cssminecraft.fabric.listener;

import com.cssbham.cssminecraft.common.event.Event;
import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.event.PlatformEventAdapter;
import com.cssbham.cssminecraft.common.event.events.PlayerJoinEvent;
import com.cssbham.cssminecraft.common.event.events.PlayerQuitEvent;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class FabricEventAdapter implements PlatformEventAdapter {

    public static EventBus EVENT_BUS;
    public static ServerExecutor EXECUTOR;

    public FabricEventAdapter(ServerExecutor executor) {
        if (null != EXECUTOR) {
            throw new IllegalStateException("singleton created twice");
        }
        EXECUTOR = executor;
    }

    @Override
    public void bindPlatformToEventBus(EventBus eventBus) {
        FabricEventAdapter.EVENT_BUS = eventBus;

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

    public static void dispatchEvent(EventBus eventBus, Event event) {
        EXECUTOR.doAsync(() -> eventBus.dispatch(event));
    }
}
