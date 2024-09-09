package com.cssbham.cssminecraft.fabric.adapter;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.UUID;

public class FabricServerChatAdapter implements ServerChatAdapter {

    private final MinecraftServer server;

    public FabricServerChatAdapter(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public void broadcastMessage(Component message) {
        server.getPlayerManager().broadcast(componentToText(message), false);
    }

    @Override
    public void sendMessageToPlayer(UUID user, Component component) {
        ServerPlayerEntity player = server.getPlayerManager().getPlayer(user);
        if (null != player) {
            player.sendMessage(componentToText(component));
        }
    }

    @Override
    public void sendMessageToConsole(Component component) {
        server.getCommandSource().sendMessage(componentToText(component));
    }

    public Text componentToText(Component component) {
        return Text.Serialization.fromJsonTree(GsonComponentSerializer.gson().serializeToTree(component), DynamicRegistryManager.EMPTY);
    }

}
