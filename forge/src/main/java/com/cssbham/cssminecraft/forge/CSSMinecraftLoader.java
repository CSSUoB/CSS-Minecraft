package com.cssbham.cssminecraft.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Entrypoint for Forge
 */
@Mod(value = "cssminecraft")
public class CSSMinecraftLoader {

    private final ForgeCSSMinecraftPlugin plugin;

    public CSSMinecraftLoader() {
        this.plugin = new ForgeCSSMinecraftPlugin();
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarted);
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
