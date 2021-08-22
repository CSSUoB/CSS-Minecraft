package com.cssbham.minecraftcore.commands;

import com.cssbham.minecraftcore.discord.DiscordBridge;
import com.cssbham.minecraftcore.util.MessageUtil;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandMakeGreen implements CommandExecutor {

    private final DiscordBridge discordBridge;

    public CommandMakeGreen(DiscordBridge discordBridge) {
        this.discordBridge = discordBridge;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }
        String arg = String.join(" ", args);
        if (!arg.matches(".{2,32}#[0-9]{4}")) {
            return false;
        }

        if (discordBridge.isMember(arg)) {
            LuckPerms perms = LuckPermsProvider.get();
            perms.getUserManager().modifyUser(((Player) sender).getUniqueId(),
                    user -> {
                        user.data().add(Node.builder("group.member").build());
                        user.data().remove(Node.builder("group.guest").build());
                    }
            );
            sender.sendMessage(MessageUtil.getMemberGreen() + "Congratulations, you are now green!");
        } else {
            sender.sendMessage(MessageUtil.getCSSPrefix() +
                    "§cIf you are a member, please link your account in Discord!\n" +
                    "Or you can buy membership at https://cssbham.com/join"
            );
        }
        return true;
    }
}
