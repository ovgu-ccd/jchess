package jchess.tests.eventbus.events;

import jchess.eventbus.events.PromotionSelectEvent;
import jchess.eventbus.events.SelectEvent;
import jchess.game.pieces.Rook;
import jchess.util.BoardCoordinate;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PromotionSelectEventTest {

    private PromotionSelectEventHandler promotionSelectEventHandler = new PromotionSelectEventHandler();
    private MBassador bus;

    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(promotionSelectEventHandler);
    }

    @Listener(references = References.Strong)
    class PromotionSelectEventHandler {
        private int messageCounter = 0;

        @Handler(delivery = Invoke.Synchronously)
        public void handlePromotionSelectEventFromGame(PromotionSelectEvent promotionSelectEvent) {
            if (promotionSelectEvent.shouldReceive(null)) {
                messageCounter = getMessageCounter() + 1;
            }
        }

        @Handler(delivery = Invoke.Synchronously)
        public void handlePromotionSelectEventFromIOSystem(PromotionSelectEvent promotionSelectEvent) {
            if (promotionSelectEvent.shouldRelay(null) && true /* this.player.isActive() */) {
                bus.publish(new PromotionSelectEvent(promotionSelectEvent));
                messageCounter = getMessageCounter() + 1;
            }
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }

    @Test
    public void testGetPieceClass() throws Exception {
        PromotionSelectEvent promotionSelectEvent = new PromotionSelectEvent(null, Rook.class);
        assertEquals(Rook.class, promotionSelectEvent.getPieceClass());
    }

    @Test
    public void testSendPromotionSelectEvent() throws Exception {
        PromotionSelectEvent promotionSelectEvent = new PromotionSelectEvent(null, Rook.class);
        bus.publish(promotionSelectEvent);

        assertEquals(2, promotionSelectEventHandler.getMessageCounter());
    }
}