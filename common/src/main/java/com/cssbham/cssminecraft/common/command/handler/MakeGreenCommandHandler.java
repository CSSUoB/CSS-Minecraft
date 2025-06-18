package com.cssbham.cssminecraft.common.command.handler;

import com.cssbham.cssminecraft.common.command.CommandContext;
import com.cssbham.cssminecraft.common.command.CommandHandler;
import com.cssbham.cssminecraft.common.command.CommandSender;
import com.cssbham.cssminecraft.common.discord.DiscordClientService;
import com.cssbham.cssminecraft.common.permission.PermissionPluginService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.concurrent.ExecutionException;

public class MakeGreenCommandHandler implements CommandHandler {

    private static final String DISCORD_USERNAME_PATTERN = "[a-z0-9._]{2,32}";

    private final DiscordClientService discordClientService;
    private final PermissionPluginService permissionPluginService;

    public MakeGreenCommandHandler(DiscordClientService discordClientService, PermissionPluginService permissionPluginService) {
        this.discordClientService = discordClientService;
        this.permissionPluginService = permissionPluginService;
    }

    @Override
    public void handle(CommandSender sender, CommandContext context) {
        if (!permissionPluginService.isAvailable()) {
            sender.sendMessage(Component.text("There is no permissions plugin available.").color(NamedTextColor.RED));
            return;
        }

        if (sender.isConsole()) {
            sender.sendMessage(Component.text("Only players may use this command.").color(NamedTextColor.RED));
            return;
        }

        String arg = String.join(" ", context.args());
        if (!arg.matches(DISCORD_USERNAME_PATTERN)) {
            sender.sendMessage(Component.text("Invalid Discord tag format.").color(NamedTextColor.RED));
            return;
        }

        if (discordClientService.getDiscordClient().isMember(arg)) {
            sender.sendMessage(Component.text("Making you green...").color(NamedTextColor.GRAY));
            try {
                permissionPluginService.grantMemberRole(sender.getUuid()).whenComplete((v, err) -> {
                    if (err != null) {
                        sender.sendMessage(Component.text("There was a problem making you green. Try again later.")
                                .color(NamedTextColor.RED));
                        throw new RuntimeException(err);
                    }

                    sender.sendMessage(Component.text("Congratulations, you are now green!").color(NamedTextColor.GREEN));
                });
            } catch (Exception e) {
                sender.sendMessage(Component.text("There was a problem making you green. Try again later.")
                        .color(NamedTextColor.RED));
                throw new RuntimeException(e);
            }
        } else {
            sender.sendMessage(Component.text("You don't appear to be a ").color(NamedTextColor.RED).append(
                            Component.text("Member").color(NamedTextColor.GREEN)
                    ).append(Component.text(" on Discord! If you are, please link your account first and try again. " +
                                    "Otherwise, you can get membership at ").color(NamedTextColor.RED)
                    ).append(Component.text("https://cssbham.com/join")
                                    .clickEvent(ClickEvent.openUrl("https://cssbham.com/join"))
                                    .color(NamedTextColor.AQUA)
                                    .decorate(TextDecoration.UNDERLINED)));
        }
    }

}
