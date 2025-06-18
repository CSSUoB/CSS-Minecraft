package com.cssbham.cssminecraft.neoforge.command;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.command.AbstractCommandService;
import com.cssbham.cssminecraft.common.command.CommandContext;
import com.cssbham.cssminecraft.common.command.CommandSender;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;
import com.cssbham.cssminecraft.common.util.CommandUtil;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class ForgeCommandService extends AbstractCommandService {

    private final ServerChatAdapter chatAdapter;

    public ForgeCommandService(Logger logger, ServerExecutor executor, ServerChatAdapter chatAdapter, MinecraftServer server) {
        super(logger, executor);

        this.chatAdapter = chatAdapter;

        for (String label : CommandUtil.ALL_COMMANDS) {
            // this is "unsafe" only because brigadier is not obfuscated
            server.getCommands().getDispatcher().register((LiteralArgumentBuilder) literal(label)
                    .executes(context -> {
                        super.execute(
                                getCommandSenderForSource((CommandSourceStack) context.getSource()),
                                new CommandContext(label, new String[0])
                        );
                        return 1;
                    })
                    .then(argument("args", greedyString())
                    .executes(context -> {
                        super.execute(
                                getCommandSenderForSource((CommandSourceStack) context.getSource()),
                                new CommandContext(label, getString(context, "args").split(" "))
                        );
                        return 1;
                    })));
        }
    }

    private CommandSender getCommandSenderForSource(CommandSourceStack source) {
        if (source.isPlayer()) {
            return new CommandSender(
                    chatAdapter,
                    source.getPlayer().getUUID(),
                    source.getTextName(),
                    false
            );
        } else {
            return new CommandSender(
                    chatAdapter,
                    new UUID(0, 0),
                    source.getTextName(),
                    true
            );
        }
    }
}
