package com.cssbham.cssminecraft.bukkit.command;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.command.AbstractCommandService;
import com.cssbham.cssminecraft.common.command.CommandContext;
import com.cssbham.cssminecraft.common.command.CommandSender;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BukkitCommandService extends AbstractCommandService implements CommandExecutor {

    private ServerChatAdapter chatAdapter;

    public BukkitCommandService(Logger logger, ServerExecutor executor, ServerChatAdapter chatAdapter) {
        super(logger, executor);

        this.chatAdapter = chatAdapter;
    }

    @Override
    public boolean onCommand(@NotNull org.bukkit.command.CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandSender commandSender;
        if (sender instanceof Player player) {
            commandSender = new CommandSender(
                    chatAdapter,
                    player.getUniqueId(),
                    player.getName(),
                    false
            );
        } else {
            commandSender = new CommandSender(
                    chatAdapter,
                    new UUID(0, 0),
                    sender.getName(),
                    true
            );
        }

        CommandContext context = new CommandContext(label, args);

        super.execute(commandSender, context);
        return true;
    }
}
