package jchess.eventbus.events;

import jchess.eventbus.Controller;

/**
 * Created by Severin Orth on 07.12.14.
 * <p/>
 * Base class for all events
 *
 * @trace [$REQ07]
 */
public abstract class Event {

    /**
     * Schedule event
     */
    public void emit() {
        Controller.INSTANCE.post(this);
    }
}
