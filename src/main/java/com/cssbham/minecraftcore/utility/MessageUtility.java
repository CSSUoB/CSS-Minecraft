package com.cssbham.minecraftcore.utility;

import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the message utility.
 */
public class MessageUtility {

    public static String getDiscordPrefix() {
        return ChatColor.of("#738abd") + "[Discord]" + ChatColor.RESET;
    }

    public static String getCSSPrefix() {
        return ChatColor.of("#4a9efe") + "[CSS]" + ChatColor.RESET;
    }

    public static String getMemberGreen() {
        return MessageUtility.getChatColor("#03e421");
    }

    public static String getChatColor(String color) {
        return ChatColor.of(color).toString();
    }

    public static String sanitise(String message) {
        return ChatColor.stripColor(MarkdownSanitizer.sanitize(message));
    }

    /**
     * Used to translate the color codes.
     * Normally translating them from & to § so colors
     * are registered.
     *
     * @param message The message to translate.
     * @return The requested translation.
     */
    public static @NotNull String translate(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
