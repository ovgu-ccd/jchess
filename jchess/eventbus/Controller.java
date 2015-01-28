package jchess.eventbus;

import jchess.eventbus.events.Event;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;

/**
 * Created by Severin Orth on 07.12.14.
 * <p/>
 * Singleton for our event bus
 *
 * @trace [$REQ07]
 */
public class Controller {
    public static final Controller INSTANCE;

    private final MBassador<Event> bus;


    @SuppressWarnings("deprecation")
    private Controller() {
        bus = new MBassador<>(BusConfiguration.SyncAsync());
    }


    static {
        INSTANCE = new Controller();
    }

    /**
     * This method subscribes an object to the MBassador event bus.
     * @param bean Object which is registered with the event bus
     */
    public void subscribe(Object bean) {
        this.bus.subscribe(bean);
    }

    /**
     * Publish an event on the MBassador event bus.
     * @param event Event to be published on to the bus
     */
    public void post(Event event) {
        this.bus.publishAsync(event);
    }
}
