package com.cssbham.cssminecraft.forge.listener;

import com.cssbham.cssminecraft.common.event.Event;
import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.event.PlatformEventAdapter;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

public class ForgeEventAdapter implements PlatformEventAdapter {

    private EventBus eventBus;

    @Override
    public void bindPlatformToEventBus(EventBus eventBus) {
        this.eventBus = eventBus;

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void dispatchEvent(Event event) {
        Objects.requireNonNull(event, "event bus not bound");

        eventBus.dispatch(event);
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
}
