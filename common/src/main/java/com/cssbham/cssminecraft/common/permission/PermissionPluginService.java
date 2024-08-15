package com.cssbham.cssminecraft.common.permission;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * A permission plugin service. This service wraps the function calls
 * of a permissions plugin.
 */
public interface PermissionPluginService {

    /**
     * Grant the Member role to a player
     *
     * @param player the player uuid
     * @return a future
     */
    CompletableFuture<Void> grantMemberRole(UUID player);

    /**
     * Get whether the service is usable
     *
     * @return true if it can be used, false otherwise
     */
    boolean isAvailable();

}
