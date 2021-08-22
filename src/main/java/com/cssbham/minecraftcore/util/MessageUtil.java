package com.cssbham.minecraftcore.util;

import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import net.md_5.bungee.api.ChatColor;

public class MessageUtil {

    public static String getDiscordPrefix() {
        return ChatColor.of("#738abd") + "[Discord]" + ChatColor.RESET;
    }

    public static String getCSSPrefix() {
        return ChatColor.of("#4a9efe") + "[CSS]" + ChatColor.RESET;
    }

    public static String getMemberGreen() {
        return MessageUtil.getChatColor("#03e421");
    }

    public static String getChatColor(String color) {
        return ChatColor.of(color).toString();
    }

    public static String sanitise(String message) {
        return ChatColor.stripColor(MarkdownSanitizer.sanitize(message));
    }
}
