package com.cssbham.cssminecraft.common.permission;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@link PermissionPluginService} implementation for LuckPerms
 */
public class LuckPermsPermissionPluginService implements PermissionPluginService {

    @Override
    public CompletableFuture<Void> grantMemberRole(UUID player) {
        LuckPerms perms = LuckPermsProvider.get();
        return perms.getUserManager().modifyUser(player,
                user -> {
                    user.data().add(Node.builder("group.member").build());
                    user.data().remove(Node.builder("group.guest").build());
                }
        );
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

}
