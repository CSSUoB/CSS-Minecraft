package com.cssbham.cssminecraft.forge.listener;

import com.cssbham.cssminecraft.common.event.Event;
import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.event.PlatformEventAdapter;
import com.cssbham.cssminecraft.common.event.events.PlayerJoinEvent;
import com.cssbham.cssminecraft.common.event.events.PlayerQuitEvent;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class ForgeEventAdapter implements PlatformEventAdapter {

    private final MinecraftServer server;
    private final ServerExecutor executor;

    public ForgeEventAdapter(MinecraftServer server, ServerExecutor executor) {
        this.server = server;
        this.executor = executor;
    }

    private EventBus eventBus;

    @Override
    public void bindPlatformToEventBus(EventBus eventBus) {
        this.eventBus = eventBus;

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void dispatchEvent(Event event) {
        Objects.requireNonNull(event, "event bus not bound");

        executor.doAsync(() -> eventBus.dispatch(event));
    }

    @SubscribeEvent
    public void onChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String name = event.getUsername();

        dispatchEvent(new ServerMessageEvent(
                player.getUUID(),
                name,
                (null == player.getDisplayName()) ? name : player.getDisplayName().getString(),
                event.getRawText()
        ));
    }

    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        String name = player.getName().getString();

        dispatchEvent(new PlayerJoinEvent(
                player.getUUID(),
                name,
                (null == player.getDisplayName()) ? name : player.getDisplayName().getString(),
                server.getPlayerCount() + 1
        ));
    }

    @SubscribeEvent
    public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        String name = player.getName().getString();

        dispatchEvent(new PlayerQuitEvent(
                player.getUUID(),
                name,
                (null == player.getDisplayName()) ? name : player.getDisplayName().getString(),
                server.getPlayerCount() - 1
        ));
    }
}
