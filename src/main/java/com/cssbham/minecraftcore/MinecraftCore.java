package com.cssbham.minecraftcore;

import com.cssbham.minecraftcore.command.CommandMakeGreen;
import com.cssbham.minecraftcore.discord.DiscordBridge;
import com.cssbham.minecraftcore.discord.DiscordRuntimeException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Main plugin class.
 */
public final class MinecraftCore extends JavaPlugin implements Listener {

    private static DiscordBridge discordBridge;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onEnable() {
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
        this.getServer().getPluginManager().registerEvents(this, this);

        // Setup commands.
        this.getCommand("makegreen").setExecutor(new CommandMakeGreen(MinecraftCore.discordBridge));

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
