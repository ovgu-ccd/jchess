package jchess.gui;

import jchess.Board;
import jchess.ChatMessage;
import jchess.GUIUtils;
import jchess.IOSystem;

/**
 * Created by andreas on 06.12.14.
 */
public class GUIConnector implements IOSystem {

    GUIUtils gui = GUIUtils.getInstance();
    private GameTab gameTab;


    public GUIConnector(GameTab gameTab) {
        this.gameTab = gameTab;
    }


    @Override public void handleUpdate(Board board) {

    }


    @Override public void handleNextPlayer() {

    }


    @Override
    public void handlePostMessage(ChatMessage chatMessage) {

    }
}
