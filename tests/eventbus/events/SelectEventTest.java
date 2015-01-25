package tests.eventbus.events;

import jchess.eventbus.events.SelectEvent;
import jchess.util.BoardCoordinate;
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
 * This class tests the SelectEvent
 *
 * @trace [REQ07]
 * @trace [REQ34]
 */
public class SelectEventTest {

    private final SelectEventHandler selectEventHandler = new SelectEventHandler();
    private MBassador bus;

    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(selectEventHandler);
    }

    @Test
    public void testGetBoardCoordinate() throws Exception {
        SelectEvent selectEvent = new SelectEvent(null, new BoardCoordinate(23, 42));
        assertEquals(selectEvent.getBoardCoordinate().getA(), 23);
        assertEquals(selectEvent.getBoardCoordinate().getB(), 42);
    }

    @Test
    public void testSendSelectEvent() throws Exception {
        SelectEvent selectEvent = new SelectEvent(null, new BoardCoordinate(1, 2));
        bus.publish(selectEvent);

        assertEquals(2, selectEventHandler.getMessageCounter());
    }

    @Listener(references = References.Strong)
    class SelectEventHandler {
        private int messageCounter = 0;

        @Handler(delivery = Invoke.Synchronously)
        public void handleSelectEventFromGame(SelectEvent selectEvent) {
            if (selectEvent.shouldReceive(null)) {
                messageCounter = getMessageCounter() + 1;
            }
        }

        @Handler(delivery = Invoke.Synchronously)
        public void handleSelectEventFromIOSystem(SelectEvent selectEvent) {
            if (selectEvent.shouldRelay(null) /* this.player.isActive() */) {
                bus.publish(new SelectEvent(selectEvent));
                messageCounter = getMessageCounter() + 1;
            }
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }
}