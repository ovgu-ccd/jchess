package jchess.tests.eventbus.events;

import jchess.eventbus.events.PossiblePromotionsEvent;
import jchess.game.pieces.Bishop;
import jchess.game.pieces.Knight;
import jchess.game.pieces.Piece;
import jchess.game.pieces.Queen;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class PossiblePromotionsEventTest {

    private PossiblePromotionsEventHandler possiblePromotionsEventHandler = new PossiblePromotionsEventHandler();
    private MBassador bus;

    @Before
    public void setUp() throws Exception {
        bus = new MBassador(BusConfiguration.SyncAsync());
        bus.subscribe(possiblePromotionsEventHandler);
    }

    @Listener(references = References.Strong)
    class PossiblePromotionsEventHandler {
        private int messageCounter = 0;

        @Handler(delivery = Invoke.Synchronously)
        public void handlePossiblePromotionsEventFromBoardView(PossiblePromotionsEvent possiblePromotionsEvent) {
            if (possiblePromotionsEvent.shouldReceive(null)) {
                messageCounter = getMessageCounter() + 1;
            }
        }

        @Handler(delivery = Invoke.Synchronously)
        public void handlePossiblePromotionsEventFromIOSystem(PossiblePromotionsEvent possiblePromotionsEvent) {
            if (possiblePromotionsEvent.shouldRelay(null) && true /* this.player.isActive() */) {
                bus.publish(new PossiblePromotionsEvent(possiblePromotionsEvent));
                messageCounter = getMessageCounter() + 1;
            }
        }

        public int getMessageCounter() {
            return messageCounter;
        }
    }

    @Test
    public void testGetPossiblePromotions() throws Exception {
        HashSet<Class<? extends Piece>> possiblePromotions = new HashSet<>();
        possiblePromotions.add(Queen.class);
        possiblePromotions.add(Bishop.class);
        possiblePromotions.add(Knight.class);
        PossiblePromotionsEvent possiblePromotionsEvent = new PossiblePromotionsEvent(null, possiblePromotions);

        assertEquals(3, possiblePromotionsEvent.getPossiblePromotions().size());
    }

    @Test
    public void testSendPossiblePromotionsEvent() throws Exception {
        PossiblePromotionsEvent possiblePromotionsEvent = new PossiblePromotionsEvent(null, new HashSet<Class<? extends Piece>>());
        bus.publish(possiblePromotionsEvent);

        assertEquals(2, possiblePromotionsEventHandler.getMessageCounter());
    }
}