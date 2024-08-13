package com.cssbham.cssminecraft.fabric.adapter;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class FabricServerChatAdapter implements ServerChatAdapter {

    private final MinecraftServer server;

    public FabricServerChatAdapter(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public void broadcastMessage(Component message) {
        server.getPlayerManager().broadcast(componentToText(message), false);
    }

    public Text componentToText(Component component) {
        return Text.Serialization.fromJsonTree(GsonComponentSerializer.gson().serializeToTree(component), DynamicRegistryManager.EMPTY);
    }

}
