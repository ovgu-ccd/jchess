package tests.eventbus.events;

import jchess.eventbus.events.PromotionSelectEvent;
import jchess.game.pieces.Rook;
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
 * Test for allowing to promote a piece
 *
 * @trace [$REQ29]
 */
public class PromotionSelectEventTest {

    private final PromotionSelectEventHandler promotionSelectEventHandler = new PromotionSelectEventHandler();
    private MBassador bus;

    @SuppressWarnings("deprecation")
    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(promotionSelectEventHandler);
    }

    @Test
    public void testGetPieceClass() throws Exception {
        PromotionSelectEvent promotionSelectEvent = new PromotionSelectEvent(null, Rook.class);
        assertEquals(Rook.class, promotionSelectEvent.getPieceClass());
    }

    @Test
    public void testSendPromotionSelectEvent() throws Exception {
        PromotionSelectEvent promotionSelectEvent = new PromotionSelectEvent(null, Rook.class);
        //noinspection unchecked
        bus.publish(promotionSelectEvent);

        assertEquals(2, promotionSelectEventHandler.getMessageCounter());
    }

    @Listener(references = References.Strong)
    class PromotionSelectEventHandler {
        private int messageCounter = 0;

        @SuppressWarnings("UnusedDeclaration")
        @Handler(delivery = Invoke.Synchronously)
        public void handlePromotionSelectEventFromGame(PromotionSelectEvent promotionSelectEvent) {
            if (promotionSelectEvent.shouldReceive(null)) {
                messageCounter = getMessageCounter() + 1;
            }
        }

        @SuppressWarnings("UnusedDeclaration")
        @Handler(delivery = Invoke.Synchronously)
        public void handlePromotionSelectEventFromIOSystem(PromotionSelectEvent promotionSelectEvent) {
            if (promotionSelectEvent.shouldRelay(null) /* this.player.isActive() */) {
                //noinspection unchecked
                bus.publish(new PromotionSelectEvent(promotionSelectEvent));
                messageCounter = getMessageCounter() + 1;
            }
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }
}