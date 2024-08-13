package com.cssbham.cssminecraft.common.event;

import java.util.*;

public class SimpleEventBus implements EventBus {

    private final Map<Class<?>, List<EventHandler<?>>> handlers = new HashMap<>();

    public void dispatch(Event event) {
        var handlers = this.handlers.getOrDefault(event.getClass(), new ArrayList<>());

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
