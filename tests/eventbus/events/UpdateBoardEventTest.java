package tests.eventbus.events;

import jchess.eventbus.events.UpdateBoardEvent;
import jchess.game.Game;
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
 * Test for the redraw event
 *
 * @trace [$REQ07]
 */
public class UpdateBoardEventTest {

    private final UpdateBoardEventHandler updateBoardEventHandler = new UpdateBoardEventHandler();
    private MBassador bus;

    @SuppressWarnings("deprecation")
    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(updateBoardEventHandler);
    }

    @Test
    public void testSendUpdateBoardEvent() throws Exception {
        UpdateBoardEvent updateBoardEvent = new UpdateBoardEvent((Game) null);
        //noinspection unchecked
        bus.publish(updateBoardEvent);

        assertEquals(2, updateBoardEventHandler.getMessageCounter());
    }

    @Listener(references = References.Strong)
    class UpdateBoardEventHandler {
        private int messageCounter = 0;

        @SuppressWarnings("UnusedDeclaration")
        @Handler(delivery = Invoke.Synchronously)
        public void handleUpdateBoardEventFromBoardView(UpdateBoardEvent updateBoardEvent) {
            if (updateBoardEvent.shouldReceive(null)) {
                messageCounter = getMessageCounter() + 1;
            }
        }

        @SuppressWarnings("UnusedDeclaration")
        @Handler(delivery = Invoke.Synchronously)
        public void handleSelectEventFromIOSystem(UpdateBoardEvent updateBoardEvent) {
            if (updateBoardEvent.shouldRelay(null) /* this.player.isActive() */) {
                //noinspection unchecked
                bus.publish(new UpdateBoardEvent(updateBoardEvent));
                messageCounter = getMessageCounter() + 1;
            }
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }
}