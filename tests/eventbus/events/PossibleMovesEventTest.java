package tests.eventbus.events;

import jchess.eventbus.events.PossibleMovesEvent;
import jchess.util.BoardCoordinate;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the possible moves event
 *
 * @trace [$REQ07]
 */
public class PossibleMovesEventTest {

    private PossibleMovesEventHandler possibleMovesEventHandler = new PossibleMovesEventHandler();
    private MBassador bus;

    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(possibleMovesEventHandler);
    }

    @Test
    public void testGetBoardCoordinates() throws Exception {
        PossibleMovesEvent possibleMovesEvent = new PossibleMovesEvent(null, new HashSet<BoardCoordinate>());
        assertTrue(possibleMovesEvent.getBoardCoordinates().isEmpty());
    }

    @Test
    public void testSendPossibleMovesEvent() throws Exception {
        PossibleMovesEvent possibleMovesEvent = new PossibleMovesEvent(null, null);
        bus.publish(possibleMovesEvent);

        assertEquals(2, possibleMovesEventHandler.getMessageCounter());
    }

    @Listener(references = References.Strong)
    class PossibleMovesEventHandler {
        private int messageCounter = 0;

        @Handler(delivery = Invoke.Synchronously)
        public void handleSelectEventFromBoardView(PossibleMovesEvent possibleMovesEvent) {
            if (possibleMovesEvent.shouldReceive(null)) {
                messageCounter = getMessageCounter() + 1;
            }
        }

        @Handler(delivery = Invoke.Synchronously)
        public void handleSelectEventFromIOSystem(PossibleMovesEvent possibleMovesEvent) {
            if (possibleMovesEvent.shouldRelay(null) && true /* this.player.isActive() */) {
                bus.publish(new PossibleMovesEvent(possibleMovesEvent));
                messageCounter = getMessageCounter() + 1;
            }
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }
}