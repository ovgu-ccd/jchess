package jchess.mvc.events;

/**
 * Created by Severin Orth on 19.01.15.
 * This file is part of the jchess project
 */
public class GenericErrorEvent extends Event {

    private final Object    context;
    private final Exception ex;


    public GenericErrorEvent(Object context, Exception ex) {
        this.context = context;
        this.ex = ex;
    }


    public Object getContext() {
        return context;
    }


    public Exception getException() {
        return ex;
    }
}
