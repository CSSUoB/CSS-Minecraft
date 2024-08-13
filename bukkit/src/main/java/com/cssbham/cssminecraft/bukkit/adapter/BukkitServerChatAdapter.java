package com.cssbham.cssminecraft.bukkit.adapter;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public class BukkitServerChatAdapter implements ServerChatAdapter {

    @Override
    public void broadcastMessage(Component message) {
        Bukkit.broadcast(message);
    }

}
