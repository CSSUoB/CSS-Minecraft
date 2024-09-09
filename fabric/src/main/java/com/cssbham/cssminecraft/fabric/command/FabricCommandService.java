package com.cssbham.cssminecraft.fabric.command;

import com.cssbham.cssminecraft.common.adapter.ServerChatAdapter;
import com.cssbham.cssminecraft.common.command.AbstractCommandService;
import com.cssbham.cssminecraft.common.command.CommandContext;
import com.cssbham.cssminecraft.common.command.CommandSender;
import com.cssbham.cssminecraft.common.command.CommandService;
import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;
import com.cssbham.cssminecraft.common.util.CommandUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.*;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;

import java.util.UUID;

public class FabricCommandService extends AbstractCommandService {

    private final ServerChatAdapter chatAdapter;

    public FabricCommandService(Logger logger, ServerExecutor executor, ServerChatAdapter chatAdapter, MinecraftServer server) {
        super(logger, executor);

        this.chatAdapter = chatAdapter;

        for (String label : CommandUtil.ALL_COMMANDS) {
            server.getCommandManager().getDispatcher().register(literal(label)
                    .executes(context -> {
                        super.execute(
                                getCommandSenderForSource(context.getSource()),
                                new CommandContext(label, new String[0])
                        );
                        return 1;
                    })
                    .then(argument("args", greedyString())
                    .executes(context -> {
                        super.execute(
                                getCommandSenderForSource(context.getSource()),
                                new CommandContext(label, getString(context, "args").split(" "))
                        );
                        return 1;
                    })));
        }
    }

    private CommandSender getCommandSenderForSource(ServerCommandSource source) {
        if (source.isExecutedByPlayer()) {
            return new CommandSender(
                    chatAdapter,
                    source.getPlayer().getUuid(),
                    source.getName(),
                    false
            );
        } else {
            return new CommandSender(
                    chatAdapter,
                    new UUID(0, 0),
                    source.getName(),
                    true
            );
        }
    }
}
