package com.cssbham.cssminecraft.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Entrypoint for Bukkit API
 */
public class CSSMinecraftLoader extends JavaPlugin {

    private final BukkitCSSMinecraftPlugin plugin;

    public CSSMinecraftLoader() {
        this.plugin = new BukkitCSSMinecraftPlugin(this);
    }

    @Override
    public void onEnable() {
        try {
            plugin.enable();
        } catch (Exception e) {
            plugin.getLogger().severe("Plugin initialisation failed - disabling");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        plugin.disable();
    }

}
