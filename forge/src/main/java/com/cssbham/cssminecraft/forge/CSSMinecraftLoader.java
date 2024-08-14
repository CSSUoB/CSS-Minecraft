package com.cssbham.cssminecraft.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
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

    public void onServerStarted(ServerStartedEvent event) {
        this.plugin.setServer(event.getServer());
        this.plugin.enable();
    }

}
