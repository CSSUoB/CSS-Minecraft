package com.cssbham.cssminecraft.bukkit.adapter;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitServerChatAdapter implements ServerChatAdapter {

    @Override
    public void broadcastMessage(Component message) {
        Bukkit.broadcast(message);
    }

    @Override
    public void sendMessageToPlayer(UUID user, Component component) {
        Player player = Bukkit.getPlayer(user);
        if (null != player)  {
            player.sendMessage(component);
        }
    }

    @Override
    public void sendMessageToConsole(Component component) {
        Bukkit.getConsoleSender().sendMessage(component);
    }

}
