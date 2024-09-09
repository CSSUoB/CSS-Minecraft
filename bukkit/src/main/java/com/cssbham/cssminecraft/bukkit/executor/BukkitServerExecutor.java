package com.cssbham.cssminecraft.bukkit.executor;

import com.cssbham.cssminecraft.common.executor.AsyncServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitServerExecutor extends AsyncServerExecutor {

    private final JavaPlugin plugin;

    public BukkitServerExecutor(Logger logger, JavaPlugin plugin) {
        super(logger);
        this.plugin = plugin;
    }

    @Override
    public void doSync(Runnable runnable) {
        plugin.getServer().getScheduler().runTask(plugin, runnable);
    }

}
