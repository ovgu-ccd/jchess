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


    public void subscribe(Object bean) {
        this.bus.subscribe(bean);
    }


    public void post(Event event) {
        this.bus.publishAsync(event);
    }
}
