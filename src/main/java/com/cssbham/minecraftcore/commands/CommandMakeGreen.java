package com.cssbham.minecraftcore.commands;

import com.cssbham.minecraftcore.discord.TeXBot;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandMakeGreen implements CommandExecutor {

    private final TeXBot teXBot;

    public CommandMakeGreen(TeXBot teXBot) {
        this.teXBot = teXBot;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }
        if (args.length != 1) {
            return false;
        }
        String arg = args[0];
        if (!arg.matches(".{3,32}#[0-9]{4}")) {
            return false;
        }

        if (teXBot.isMember(arg)) {
            LuckPerms perms = LuckPermsProvider.get();
            perms.getUserManager().modifyUser(
                    ((Player) sender).getUniqueId(),
                    user -> {
                        user.data().add(Node.builder("group.member").build());
                        user.data().remove(Node.builder("group.guest").build());
                    }
            );
            sender.sendMessage(ChatColor.of("#03e421") + "Congratulations, you are now green!");
        } else {
            sender.sendMessage(
                    ChatColor.of("#2f3c63") + "[CSS] " +
                            "§cIf you are a member, please link your account in Discord!\n" +
                            "Or you can buy membership at https://cssbham.com/join"

            );
        }
        return true;
    }
}
