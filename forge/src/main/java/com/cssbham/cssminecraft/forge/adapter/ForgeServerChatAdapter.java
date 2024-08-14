package com.cssbham.cssminecraft.forge.adapter;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class ForgeServerChatAdapter implements ServerChatAdapter {

    private final MinecraftServer server;

    public ForgeServerChatAdapter(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public void broadcastMessage(Component message) {
        server.getPlayerList().broadcastSystemMessage(componentToMinecraftComponent(message), false);
    }

    @Override
    public void sendMessageToPlayer(UUID user, Component component) {
        ServerPlayer player = server.getPlayerList().getPlayer(user);
        if (null != player) {
            player.sendSystemMessage(componentToMinecraftComponent(component));
        }
    }

    @Override
    public void sendMessageToConsole(Component component) {
        server.sendSystemMessage(componentToMinecraftComponent(component));
    }

    public net.minecraft.network.chat.Component componentToMinecraftComponent(Component component) {
        return net.minecraft.network.chat.Component.Serializer.fromJson(GsonComponentSerializer.gson().serializeToTree(component), RegistryAccess.EMPTY);
    }

}
