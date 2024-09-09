package com.cssbham.cssminecraft.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

/**
 * Entrypoint for Fabric
 */
public class CSSMinecraftLoader implements DedicatedServerModInitializer {

    private final FabricCSSMinecraftPlugin plugin;

    public CSSMinecraftLoader() {
        this.plugin = new FabricCSSMinecraftPlugin();
    }

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTING.register(this::onStart);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onStop);
    }

    private void onStart(MinecraftServer server) {
        this.plugin.setServer(server);
        try {
            this.plugin.enable();
        } catch (Exception e) {
            this.plugin.getLogger().severe("Mod initialisation failed - disabling");
            this.plugin.disable();
        }
    }

    private void onStop(MinecraftServer server) {
        this.plugin.disable();
    }
}
