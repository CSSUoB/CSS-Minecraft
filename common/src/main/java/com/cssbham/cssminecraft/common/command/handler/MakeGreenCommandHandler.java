package com.cssbham.cssminecraft.common.command.handler;

import com.cssbham.cssminecraft.common.command.CommandContext;
import com.cssbham.cssminecraft.common.command.CommandHandler;
import com.cssbham.cssminecraft.common.command.CommandSender;
import com.cssbham.cssminecraft.common.discord.DiscordClientService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class MakeGreenCommandHandler implements CommandHandler {

    private final DiscordClientService discordClientService;

    public MakeGreenCommandHandler(DiscordClientService discordClientService) {
        this.discordClientService = discordClientService;
    }

    @Override
    public void handle(CommandSender sender, CommandContext context) {
        if (sender.isConsole()) {
            sender.sendMessage(Component.text("Only players may use this command.").color(NamedTextColor.RED));
            return;
        }

        String arg = String.join(" ", context.args());
        if (!arg.matches("[a-z0-9._]{2,32}|.{2,32}#[0-9]{4}")) {
            sender.sendMessage(Component.text("Invalid Discord tag format.").color(NamedTextColor.RED));
            return;
        }

        if (discordClientService.getDiscordClient().isMember(arg)) {
            //TODO the luckperms stuff
            sender.sendMessage(Component.text("Congratulations, you are now green!").color(NamedTextColor.GREEN));
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
