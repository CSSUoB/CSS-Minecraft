package com.cssbham.cssminecraft.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

/**
 * Entrypoint for Forge
 */
@Mod(modid = "cssminecraft", serverSideOnly = true, acceptableRemoteVersions = "*")
public class CSSMinecraftLoader {

    private ForgeCSSMinecraftPlugin plugin;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        this.plugin = new ForgeCSSMinecraftPlugin(event.getModConfigurationDirectory().toPath());
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartingEvent event) {
        this.plugin.setServer(event.getServer());
        try {
            this.plugin.enable();
        } catch (Exception e) {
            this.plugin.getLogger().severe("Mod initialisation failed - disabling");
            this.plugin.disable();
        }
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        this.plugin.disable();
    }

}
