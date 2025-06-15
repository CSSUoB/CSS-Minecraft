package com.cssbham.cssminecraft.neoforge;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

/**
 * Entrypoint for Forge
 */
@Mod(value = "cssminecraft")
public class CSSMinecraftLoader {

    private final NeoForgeCSSMinecraftPlugin plugin;

    public CSSMinecraftLoader() {
        this.plugin = new NeoForgeCSSMinecraftPlugin();
        NeoForge.EVENT_BUS.addListener(this::onServerStarted);
        NeoForge.EVENT_BUS.addListener(this::onServerStopping);
    }

    public void onServerStarted(ServerStartingEvent event) {
        this.plugin.setServer(event.getServer());
        try {
            this.plugin.enable();
        } catch (Exception e) {
            this.plugin.getLogger().severe("Mod initialisation failed - disabling");
            this.plugin.disable();
        }
    }

    public void onServerStopping(ServerStoppingEvent event) {
        this.plugin.disable();
    }

}
