package jchess.tests.eventbus.events;

import com.sun.tools.javac.jvm.Gen;
import jchess.eventbus.events.GenericErrorEvent;
import jchess.eventbus.events.NewGameEvent;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenericErrorEventTest {

    private GenericErrorEventHandler genericErrorEventHandler = new GenericErrorEventHandler();
    private MBassador bus;

    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(genericErrorEventHandler);
    }

    @Listener(references = References.Strong)
    class GenericErrorEventHandler {
        private int messageCounter = 0;

        @Handler(delivery = Invoke.Synchronously)
        public void handleGenericErrorEventFromApplication(GenericErrorEvent genericErrorEvent) {
            messageCounter = getMessageCounter() + 1;
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }

    @Test
    public void testGetContext() throws Exception {
        GenericErrorEvent genericErrorEvent = new GenericErrorEvent(new Integer(42), null);
        assertEquals(42, genericErrorEvent.getContext());
    }

    @Test
    public void testGetException() throws Exception {
        GenericErrorEvent genericErrorEvent = new GenericErrorEvent(null, new Exception());
        assertEquals(Exception.class, genericErrorEvent.getException().getClass());
    }

    @Test
    public void testSendGenericErrorEvent() throws Exception {
        GenericErrorEvent genericErrorEvent = new GenericErrorEvent(null, new Exception());
        bus.publish(genericErrorEvent);

        assertEquals(1, genericErrorEventHandler.getMessageCounter());
    }
}