package com.cssbham.cssminecraft.common.event;

/**
 * Interface for platforms to map platform-specific events to the
 * common {@link EventBus}
 */
public interface PlatformEventAdapter {

    void bindPlatformToEventBus(EventBus eventBus);

}
