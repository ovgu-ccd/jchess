package jchess.eventbus.events;

/**
 * Base class handling any error status
 * <p/>
 * Created by Severin Orth on 19.01.15.
 * This file is part of the jchess project
 *
 * @trace [$REQ07]
 * @flow GL -> GUI
 * @flow GUI -> GUI
 */
public class GenericErrorEvent extends Event {

    private final Object context;
    private final Exception ex;


    public GenericErrorEvent(Object context, Exception ex) {
        this.context = context;
        this.ex = ex;
    }


    /**
     * Getter for the context assigned with the event.
     * @return Object context
     */
    public Object getContext() {
        return context;
    }

    /**
     * Getter for the exception assigned with this event.
     * @return Exception
     */
    public Exception getException() {
        return ex;
    }
}
