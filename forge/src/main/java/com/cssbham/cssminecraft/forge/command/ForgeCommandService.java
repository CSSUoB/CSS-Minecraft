package com.cssbham.cssminecraft.forge.command;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.command.AbstractCommandService;
import com.cssbham.cssminecraft.common.command.CommandContext;
import com.cssbham.cssminecraft.common.command.CommandSender;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;
import com.cssbham.cssminecraft.common.util.CommandUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class ForgeCommandService extends AbstractCommandService {

    public ForgeCommandService(Logger logger, ServerExecutor executor, ServerChatAdapter chatAdapter, MinecraftServer server) {
        super(logger, executor);

        CommandHandler ch = (CommandHandler) server.getCommandManager();
        for (String label : CommandUtil.ALL_COMMANDS) {
            ch.registerCommand(new CommandBase() {
                @Override
                public String getName() {
                    return label;
                }

                @Override
                public String getUsage(ICommandSender sender) {
                    return String.format("/%s", label);
                }

                @Override
                public int getRequiredPermissionLevel() {
                    return 0;
                }

                @Override
                public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
                    return true;
                }

                @Override
                public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
                    CommandSender commandSender;
                    if (sender.getCommandSenderEntity() == null)  {
                        commandSender = new CommandSender(chatAdapter, new UUID(0, 0), sender.getName(), true);
                    } else {
                        commandSender = new CommandSender(chatAdapter, sender.getCommandSenderEntity().getUniqueID(), sender.getName(), false);
                    }
                    ForgeCommandService.super.execute(commandSender, new CommandContext(label, args));
                }
            });
        }
    }
}
