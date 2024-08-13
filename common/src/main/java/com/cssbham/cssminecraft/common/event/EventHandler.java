package com.cssbham.cssminecraft.common.event;

public abstract class EventHandler<E extends Event> {

    public abstract void handle(E event);

}
