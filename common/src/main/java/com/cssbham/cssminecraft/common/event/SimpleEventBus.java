package com.cssbham.cssminecraft.common.event;

import com.cssbham.cssminecraft.common.logger.Logger;

import java.util.*;

public class SimpleEventBus implements EventBus {

    private final Map<Class<?>, List<EventHandler<?>>> handlers = new HashMap<>();
    private final Logger logger;

    public SimpleEventBus(Logger logger) {
        this.logger = logger;
    }

    public void dispatch(Event event) {
        var handlers = this.handlers.getOrDefault(event.getClass(), new ArrayList<>());
        logger.debug(String.format("Event dispatch: %s", event.getClass().getName()));

        for (EventHandler handler : handlers) {
            try {
                handler.handle(event);
            } catch (Exception e) {
                // TODO handle
            }
        }
    }

    public void subscribe(Class<?> event, EventHandler handler) {
        if (!this.handlers.containsKey(event)) {
            this.handlers.put(event, new ArrayList<>());
        }

        this.handlers.get(event).add(handler);
    }
}
