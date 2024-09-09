package com.cssbham.cssminecraft.common.command;

/**
 * Abstraction for command service, which manages command registration and
 * execution.
 */
public interface CommandService {

    /**
     * Register a command for execution with the service.
     *
     * @param label the command label
     * @param handler the command executor
     * @param aliases command aliases
     */
    void register(String label, CommandHandler handler, String... aliases);

    /**
     * Execute a command with a given command context.
     *
     * @param sender command sender
     * @param context command context
     */
    void execute(CommandSender sender, CommandContext context);

}
