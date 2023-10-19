package com.cssbham.minecraftcore.discord;

/**
 * Represents a discord runtime exception.
 * Used to throw discord related exceptions.
 */
public class DiscordRuntimeException extends RuntimeException {

    /**
     * Used to create a discord run time exception.
     *
     * @param message The error message.
     */
    public DiscordRuntimeException(String message) {
        super(message);
    }
}
