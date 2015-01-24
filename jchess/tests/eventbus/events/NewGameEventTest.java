package jchess.tests.eventbus.events;

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

/**
 * This class tests the NewGameEvent
 *
 * @trace
 */
public class NewGameEventTest {

    private NewGameEventHandler newGameEventHandler = new NewGameEventHandler();
    private MBassador bus;

    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(newGameEventHandler);
    }

    @Listener(references = References.Strong)
    class NewGameEventHandler {
        private int messageCounter = 0;

        @Handler(delivery = Invoke.Synchronously)
        public void handleNewGameEventFromApplication(NewGameEvent newGameEvent) {
            messageCounter = getMessageCounter() + 1;
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }

    @Test
    public void testGetIoSystems() throws Exception {
        NewGameEvent newGameEvent = new NewGameEvent(null, null, null);
        assertNull(newGameEvent.getIoSystems());
    }

    @Test
    public void testGetPlayerNames() throws Exception {
        NewGameEvent newGameEvent = new NewGameEvent(null, null, null);
        assertNull(newGameEvent.getPlayerNames());
    }

    @Test
    public void testGetGameTab() throws Exception {
        NewGameEvent newGameEvent = new NewGameEvent(null, null, null);
        assertNull(newGameEvent.getGameTab());
    }

    @Test
    public void testSendNewGameEvent() throws Exception {
        NewGameEvent newGameEvent = new NewGameEvent(null, null, null);
        bus.publish(newGameEvent);

        assertEquals(1, newGameEventHandler.getMessageCounter());
    }
}