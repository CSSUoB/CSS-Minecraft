package com.cssbham.cssminecraft.bukkit;

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
        plugin.enable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
