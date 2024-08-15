package com.cssbham.cssminecraft.common.handler;

import com.cssbham.cssminecraft.common.event.Event;
import com.cssbham.cssminecraft.common.event.EventHandler;

public abstract class AbstractPlayerJoinLeaveEventHandler<E extends Event> extends EventHandler<E> {

    protected String getPlayerCountMessage(int count) {
        return switch (count) {
            case 0 -> "there are now no players online";
            case 1 -> "there is now 1 player online";
            default -> "there are now " + count + " players online";
        };
    }

}
