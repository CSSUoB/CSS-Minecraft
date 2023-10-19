package com.cssbham.minecraftcore;

import com.cssbham.minecraftcore.command.MakeGreenCommand;
import com.cssbham.minecraftcore.discord.DiscordBridge;
import com.cssbham.minecraftcore.discord.DiscordRuntimeException;
import com.cssbham.minecraftcore.listener.PlayerListener;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * Main plugin class.
 */
public final class MinecraftCore extends CozyPlugin implements Listener {

    private static DiscordBridge discordBridge;

    @Override
    public boolean enableCommandDirectory() {
        return false;
    }

    @Override
    public void onCozyEnable() {
        this.saveDefaultConfig();

        // Check if discord bridge is already active.
        if (MinecraftCore.discordBridge != null) {
            MinecraftCore.discordBridge.shutdown();
        }

        try {
            MinecraftCore.discordBridge = new DiscordBridge(this);
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        // Register events.
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // Setup commands.
        this.addCommand(new MakeGreenCommand());

        // Log enable message.
        this.getLogger().info("Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().warning("Plugin has been disabled.");

        // Attempt to shut down the discord bridge.
        try {
            MinecraftCore.discordBridge.shutdown();
            MinecraftCore.discordBridge = null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Used to get the instance of the discord bridge.
     *
     * @return The instance of the discord bridge.
     */
    public static @NotNull DiscordBridge getDiscordBridge() {
        if (MinecraftCore.discordBridge == null) {
            throw new DiscordRuntimeException("Discord bridge is null.");
        }
        return MinecraftCore.discordBridge;
    }
}
