package com.cssbham.cssminecraft.common.event;

public interface EventBus {

    void dispatch(Event event);

    void subscribe(Class<?> event, EventHandler handler);

}
