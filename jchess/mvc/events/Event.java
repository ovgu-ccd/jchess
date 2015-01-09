package jchess.mvc.events;

import jchess.mvc.Controller;

/**
 * Created by Severin Orth on 07.12.14.
 *
 * Base class for all events
 */
public abstract class Event {

    /**
     * Schedule event
     */
    public void emit() {
        Controller.INSTANCE.post(this);
    }
}
