package com.cssbham.cssminecraft.bukkit.listener;

import com.cssbham.cssminecraft.common.event.Event;
import com.cssbham.cssminecraft.common.event.EventBus;
import com.cssbham.cssminecraft.common.event.PlatformEventAdapter;
import com.cssbham.cssminecraft.common.event.events.ServerMessageEvent;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BukkitEventListener implements Listener, PlatformEventAdapter {

    private final JavaPlugin plugin;
    private final ServerExecutor executor;
    private EventBus eventBus;

    public BukkitEventListener(JavaPlugin plugin, ServerExecutor executor) {
        this.plugin = plugin;
        this.executor = executor;
    }

    @Override
    public void bindPlatformToEventBus(EventBus eventBus) {
        this.eventBus = eventBus;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private void dispatchEvent(Event event) {
        Objects.requireNonNull(event, "event bus not bound");

        executor.doAsync(() -> eventBus.dispatch(event));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        dispatchEvent(new ServerMessageEvent(
                player.getUniqueId(),
                player.getName(),
                componentToString(player.displayName()),
                event.getMessage()
        ));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        dispatchEvent(new com.cssbham.cssminecraft.common.event.events.PlayerJoinEvent(
                player.getUniqueId(),
                player.getName(),
                componentToString(player.displayName()),
                plugin.getServer().getOnlinePlayers().size()
        ));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        dispatchEvent(new com.cssbham.cssminecraft.common.event.events.PlayerQuitEvent(
                player.getUniqueId(),
                player.getName(),
                componentToString(player.displayName()),
                plugin.getServer().getOnlinePlayers().size() - 1
        ));
    }

    private String componentToString(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

}
