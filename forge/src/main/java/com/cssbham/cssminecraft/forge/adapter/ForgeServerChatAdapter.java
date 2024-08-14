package com.cssbham.cssminecraft.forge.adapter;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;

public class ForgeServerChatAdapter implements ServerChatAdapter {

    private final MinecraftServer server;

    public ForgeServerChatAdapter(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public void broadcastMessage(Component message) {
        server.getPlayerList().broadcastSystemMessage(componentToMinecraftComponent(message), false);
    }

    public net.minecraft.network.chat.Component componentToMinecraftComponent(Component component) {
        return net.minecraft.network.chat.Component.Serializer.fromJson(GsonComponentSerializer.gson().serializeToTree(component), RegistryAccess.EMPTY);
    }

}
