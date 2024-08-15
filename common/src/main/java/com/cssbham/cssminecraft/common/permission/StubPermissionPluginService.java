package com.cssbham.cssminecraft.common.permission;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * A stub permission plugin service, for when no plugin is available
 */
public class StubPermissionPluginService implements PermissionPluginService {

    @Override
    public CompletableFuture<Void> grantMemberRole(UUID player) {
        throw new UnsupportedOperationException("No permission plugin available!");
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

}
