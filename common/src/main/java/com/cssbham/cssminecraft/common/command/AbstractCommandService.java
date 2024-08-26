package com.cssbham.cssminecraft.common.command;

import com.cssbham.cssminecraft.common.executor.ServerExecutor;
import com.cssbham.cssminecraft.common.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract implementation of command service. Platform implementations should
 * bind their platform-specific command framework to this.
 */
public abstract class AbstractCommandService implements CommandService {

    private final Map<String, CommandHandler> commands;
    private final Logger logger;
    private final ServerExecutor executor;

    public AbstractCommandService(Logger logger, ServerExecutor executor) {
        this.commands = new HashMap<>();
        this.logger = logger;
        this.executor = executor;
    }

    @Override
    public final void register(String label, CommandHandler handler, String... aliases) {
        commands.put(label, handler);
        for (String alias : aliases) {
            commands.put(alias, handler) ;
        }
    }

    @Override
    public final void execute(CommandSender sender, CommandContext context) {
        CommandHandler handler = commands.get(context.getLabel());

        logger.debug(String.format("Handler for /%s executed by %s (%s): %s",
                context.getLabel(),
                sender.getName(),
                sender.getUuid(),
                (null == handler) ? null : handler.getClass().getName()
        ));

        if (null == handler) {
            return;
        }

        executor.doAsync(() -> {
            try {
                handler.handle(sender, context);
            } catch (Exception e) {
                logger.severe(String.format("Exception handling command /%s for %s: %s",
                        context.getLabel(),
                        sender.getName(),
                        e.getMessage()
                    ));
                e.printStackTrace();
            }
        });
    }

}
