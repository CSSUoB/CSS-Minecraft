package com.cssbham.minecraftcore.command;

import com.cssbham.minecraftcore.MinecraftCore;
import com.cssbham.minecraftcore.utility.MessageUtility;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.command.datatype.*;
import com.github.cozyplugins.cozylibrary.pool.PermissionPool;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the make green command.
 * This command is used to update your permissions
 * on the server if you are a member in discord.
 */
public class MakeGreenCommand implements CozyCommand {

    @Override
    public @NotNull String getName() {
        return "makegreen";
    }

    @Override
    public @Nullable CommandAliases getAliases() {
        return new CommandAliases().append("mg").append("green");
    }

    @Override
    public @Nullable String getDescription() {
        return "Make yourself green by verifying your CSS membership.";
    }

    @Override
    public @Nullable String getSyntax() {
        return "/<command> [Discord Username#1234]";
    }

    @Override
    public @Nullable PermissionPool getPermissionPool() {
        return null;
    }

    @Override
    public @Nullable CommandPool getSubCommands() {
        return null;
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull CommandArguments arguments) {
        return new CommandSuggestions().append(new String[]{"<Name#number>"});
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onPlayerUser(@NotNull PlayerUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {

        // Check if they have given arguments.
        if (arguments.getArguments().isEmpty()) {
            user.sendMessage("&7Incorrect arguments. &f" + this.getSyntax());
            return new CommandStatus();
        }

        // Get the first argument.
        String discordUsername = arguments.getArguments().get(0);

        // Check if the username is in the correct format.
        if (!discordUsername.matches(".{2,32}#[0-9]{4}")) {
            user.sendMessage("&7Incorrect arguments. &f" + this.getSyntax());
            return new CommandStatus();
        }

        // Check if they are a member.
        if (!MinecraftCore.getDiscordBridge().isMember(discordUsername)) {
            user.sendMessage(MessageUtility.getCSSPrefix() +
                    "&cIf you are a member, please link your account in Discord!\n" +
                    "&fOr you can buy membership at https://cssbham.com/join"
            );
            return new CommandStatus();
        }

        // Otherwise they are a member.
        LuckPerms luckPerms = LuckPermsProvider.get();
        luckPerms.getUserManager().modifyUser(
                user.getUuid(),
                luckPermsUser -> {
                    luckPermsUser.data().add(Node.builder("group.member").build());
                    luckPermsUser.data().remove(Node.builder("group.guest").build());
                }
        );

        // Send message.
        user.sendMessage(MessageUtility.getMemberGreen() + "Congratulations, you are now green!");
        return new CommandStatus();
    }

    @Override
    public @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onConsoleUser(@NotNull ConsoleUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }
}
