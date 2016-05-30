package sample;

import lombok.Getter;
import lombok.Setter;
import sample.controllers.CommunicationController;
import sample.controllers.LocalTimeLabelsController;
import sample.models.CheckMessage;
import sample.models.CheckMatMessage;
import sample.models.Sounds;
import sample.services.ChessLogicService;
import sample.services.CounterService;
import sample.services.HistoryService;
import sample.services.TCPConnectionService;

/**
 * To bedzie glowny singleton odwolujacy sie do wszystkiego
 * Bedzie wywolywal i obslugiwal wszystkie pozostale moduly programu
 */
public class GameEngine {
    private static GameEngine gameEngine = null;

    @Getter @Setter
    CounterService counterService;

    @Getter @Setter
    int timeGameMode;

    @Getter @Setter
    int checkState;

    @Getter @Setter
    String nick;

    @Getter @Setter
    TCPConnectionService tcpConnectionService;

    @Setter @Getter
    boolean serverRole;

    @Getter @Setter
    ChessLogicService chessLogicService;

    @Getter
    CommunicationController communicationController;

    @Getter @Setter
    LocalTimeLabelsController localTimeLabelsController;

    @Getter @Setter
    HistoryService historyService = new HistoryService();

    public void setCommunicationController(CommunicationController cc){
        communicationController=cc;
    }


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
        GameEngine.getInstance().getHistoryService().addBoard(chessLogicService.getBoard());
        chessLogicService.getBoard().setBoard(chessLogicService.move(chessLogicService.getBoard().getBoard(), moveX, moveY, x, y));
        checkState = chessLogicService.getCheck();

        if(checkState!=-1){
            if(chessLogicService.getMat()){
                Sounds.getInstance().getSound(4);
                tcpConnectionService.sendObject(new CheckMatMessage());
            }
            else{
                Sounds.getInstance().getSound(3);
                tcpConnectionService.sendObject(new CheckMessage(checkState));
            }
        }

        // change player
        chessLogicService.getBoard().setServerTurn(!serverRole);

        // synchronize with remote
        tcpConnectionService.sendObject(chessLogicService.getBoard());

        GameEngine.getInstance().getCounterService().stopTiming();
    }

    // 0 if OK
    // -1 if white checked
    // 1  if black checked
    // -2 if white checkedMated
    // 2 if black check mated
    public int localMove(int x, int y){
        GameEngine.getInstance().getHistoryService().addBoard(chessLogicService.getBoard());
        chessLogicService.getBoard().setBoard(chessLogicService.move(chessLogicService.getBoard().getBoard(), moveX, moveY, x, y));
        checkState = chessLogicService.getCheck();

        int returnCode=0;
        if(checkState==-1) {
            returnCode= 0;
        }
        else if(checkState==1) {
            if (chessLogicService.getMat()) {
                returnCode = 2;
            } else {
                returnCode = 1;
            }
        }
        else {
            if (chessLogicService.getMat()) {
                returnCode = -2;
            } else {
                returnCode = -1;
            }
        }

        // change player
        chessLogicService.getBoard().setServerTurn(!serverRole);
        serverRole = !serverRole;

        return returnCode;

    }

}
