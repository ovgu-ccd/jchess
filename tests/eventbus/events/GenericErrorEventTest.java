package tests.eventbus.events;


import jchess.eventbus.events.GenericErrorEvent;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the generic error event
 *
 * @trace [$REQ36]
 */
public class GenericErrorEventTest {

    private final GenericErrorEventHandler genericErrorEventHandler = new GenericErrorEventHandler();
    private MBassador bus;

    @SuppressWarnings("deprecation")
    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(genericErrorEventHandler);
    }

    @Test
    public void testGetContext() throws Exception {
        GenericErrorEvent genericErrorEvent = new GenericErrorEvent(42, null);
        assertEquals(42, genericErrorEvent.getContext());
    }

    @Test
    public void testGetException() throws Exception {
        GenericErrorEvent genericErrorEvent = new GenericErrorEvent(null, new Exception());
        //noinspection ThrowableResultOfMethodCallIgnored
        assertEquals(Exception.class, genericErrorEvent.getException().getClass());
    }

    @Test
    public void testSendGenericErrorEvent() throws Exception {
        GenericErrorEvent genericErrorEvent = new GenericErrorEvent(null, new Exception());
        //noinspection unchecked
        bus.publish(genericErrorEvent);

        assertEquals(1, genericErrorEventHandler.getMessageCounter());
    }

    @Listener(references = References.Strong)
    class GenericErrorEventHandler {
        private int messageCounter = 0;

        @SuppressWarnings({"UnusedParameters", "UnusedDeclaration"})
        @Handler(delivery = Invoke.Synchronously)
        public void handleGenericErrorEventFromApplication(GenericErrorEvent genericErrorEvent) {
            messageCounter = getMessageCounter() + 1;
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }
}