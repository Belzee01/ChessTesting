package sample;

import lombok.Getter;
import lombok.Setter;
import sample.controllers.CommunicationController;
import sample.models.CheckMessage;
import sample.services.ChessLogicService;
import sample.services.TCPConnectionService;

/**
 * To bedzie glowny singleton odwolujacy sie do wszystkiego
 * Bedzie wywolywal i obslugiwal wszystkie pozostale moduly programu
 */
public class GameEngine {
    private static GameEngine gameEngine = null;

    @Getter @Setter
    int checkState;

    @Getter @Setter
    String nick;

    @Getter @Setter
    TCPConnectionService tcpConnectionService;

    @Getter @Setter
    boolean serverRole;

    @Getter @Setter
    ChessLogicService chessLogicService;

    @Getter @Setter
    CommunicationController communicationController;

    public GameEngine(){}

    public static GameEngine getInstance(){
        if(gameEngine == null)
            gameEngine = new GameEngine();
        return gameEngine;
    }

    @Getter @Setter
    private int moveX;
    @Getter @Setter
    private int moveY;
    public static final String COLOR = "WHITE";

    public void move(int x, int y) {
        chessLogicService.getBoard().setBoard(chessLogicService.move(chessLogicService.getBoard().getBoard(), moveX, moveY, x, y));

        // change player
        chessLogicService.getBoard().setServerTurn(!serverRole);

        // synchronize with remote
        tcpConnectionService.sendObject(chessLogicService.getBoard());

        checkState = chessLogicService.getCheck();
        if (checkState != -1) {
            tcpConnectionService.sendObject(new CheckMessage(checkState));
        }

    }
}
