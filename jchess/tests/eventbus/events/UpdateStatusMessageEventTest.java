package jchess.tests.eventbus.events;

import jchess.eventbus.events.UpdateStatusMessageEvent;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdateStatusMessageEventTest {

    private UpdateStatusMessageEventHandler updateStatusMessageEventHandler = new UpdateStatusMessageEventHandler();
    private MBassador bus;

    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(updateStatusMessageEventHandler);
    }

    @Listener(references = References.Strong)
    class UpdateStatusMessageEventHandler {
        private int messageCounter = 0;

        @Handler(delivery = Invoke.Synchronously)
        public void handleUpdateStatusMessageEventFromBoardView(UpdateStatusMessageEvent updateStatusMessageEvent) {
            if (updateStatusMessageEvent.shouldReceive(null)) {
                messageCounter = getMessageCounter() + 1;
            }
        }

        @Handler(delivery = Invoke.Synchronously)
        public void handleUpdateStatusMessageEventFromIOSystem(UpdateStatusMessageEvent updateStatusMessageEvent) {
            if (updateStatusMessageEvent.shouldRelay(null) && true /* this.player.isActive() */) {
                bus.publish(new UpdateStatusMessageEvent(updateStatusMessageEvent));
                messageCounter = getMessageCounter() + 1;
            }
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }

    @Test
    public void testGetStatusMessage() throws Exception {
        UpdateStatusMessageEvent updateStatusMessageEvent = new UpdateStatusMessageEvent(null, "TEST", UpdateStatusMessageEvent.Types.NORMAL);
        assertEquals("TEST", updateStatusMessageEvent.getStatusMessage());
    }

    @Test
    public void testGetTypes() throws Exception {
        UpdateStatusMessageEvent updateStatusMessageEvent = new UpdateStatusMessageEvent(null, "TEST", UpdateStatusMessageEvent.Types.NORMAL);
        assertEquals(UpdateStatusMessageEvent.Types.NORMAL, updateStatusMessageEvent.getTypes());
    }

    @Test
    public void testSendUpdateStatusMessageEvent() throws Exception {
        UpdateStatusMessageEvent updateStatusMessageEvent = new UpdateStatusMessageEvent(null, "TEST", UpdateStatusMessageEvent.Types.NORMAL);
        bus.publish(updateStatusMessageEvent);

        assertEquals(2, updateStatusMessageEventHandler.getMessageCounter());
    }
}