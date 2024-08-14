package com.cssbham.cssminecraft.common.event;

/**
 * Base interface for an event bus.
 */
public interface EventBus {

    /**
     * Dispatch an event.
     *
     * @param event the event to dispatch
     */
    void dispatch(Event event);

    /**
     * Subscribe to an event.
     *
     * @param event the event to subscribe to
     * @param handler the event handler
     */
    void subscribe(Class<?> event, EventHandler handler);

}
