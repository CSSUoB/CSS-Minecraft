package com.cssbham.cssminecraft.forge.adapter;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;

import java.util.UUID;

public class ForgeServerChatAdapter implements ServerChatAdapter {

    private final MinecraftServer server;

    public ForgeServerChatAdapter(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public void broadcastMessage(Component message) {
        server.getPlayerList().sendMessage(componentToMinecraftComponent(message), false);
    }

    @Override
    public void sendMessageToPlayer(UUID user, Component component) {
        EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(user);
        if (null != player) {
            player.sendMessage(componentToMinecraftComponent(component));
        }
    }

    @Override
    public void sendMessageToConsole(Component component) {
        server.sendMessage(componentToMinecraftComponent(component));
    }

    public ITextComponent componentToMinecraftComponent(Component component) {
        return ITextComponent.Serializer.fromJsonLenient(GsonComponentSerializer.colorDownsamplingGson().serializeToTree(component).toString());
    }

}
