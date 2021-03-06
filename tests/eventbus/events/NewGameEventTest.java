package tests.eventbus.events;

import jchess.eventbus.events.NewGameEvent;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * This class tests the NewGameEvent
 *
 * @trace [$REQ33]
 */
public class NewGameEventTest {

    private final NewGameEventHandler newGameEventHandler = new NewGameEventHandler();
    private MBassador bus;

    @SuppressWarnings("deprecation")
    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(newGameEventHandler);
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
        //noinspection unchecked
        bus.publish(newGameEvent);

        assertEquals(1, newGameEventHandler.getMessageCounter());
    }

    @Listener(references = References.Strong)
    class NewGameEventHandler {
        private int messageCounter = 0;

        @SuppressWarnings({"UnusedParameters", "UnusedDeclaration"})
        @Handler(delivery = Invoke.Synchronously)
        public void handleNewGameEventFromApplication(NewGameEvent newGameEvent) {
            messageCounter = getMessageCounter() + 1;
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }
}