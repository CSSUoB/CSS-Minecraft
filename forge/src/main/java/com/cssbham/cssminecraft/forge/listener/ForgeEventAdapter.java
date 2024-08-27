package com.cssbham.cssminecraft.forge.listener;

import com.cssbham.cssminecraft.common.event.Event;
import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.event.PlatformEventAdapter;
import com.cssbham.cssminecraft.common.event.events.PlayerJoinEvent;
import com.cssbham.cssminecraft.common.event.events.PlayerQuitEvent;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

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
        EntityPlayerMP player = event.getPlayer();
        String name = event.getUsername();

        dispatchEvent(new ServerMessageEvent(
                player.getUniqueID(),
                name,
                (null == player.getDisplayName()) ? name : player.getDisplayName().getUnformattedText(),
                event.getMessage()
        ));
    }

    @SubscribeEvent
    public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        String name = player.getName();

        dispatchEvent(new PlayerJoinEvent(
                player.getUniqueID(),
                name,
                (null == player.getDisplayName()) ? name : player.getDisplayName().getUnformattedText(),
                server.getCurrentPlayerCount()
        ));
    }

    @SubscribeEvent
    public void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayer player = event.player;
        String name = player.getName();

        dispatchEvent(new PlayerQuitEvent(
                player.getUniqueID(),
                name,
                (null == player.getDisplayName()) ? name : player.getDisplayName().getUnformattedText(),
                server.getCurrentPlayerCount() - 1
        ));
    }
}
