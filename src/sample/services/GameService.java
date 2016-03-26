package sample.services;

import lombok.Getter;
import lombok.Setter;
import sample.models.Board;

/**
 * Symulacja GameEngine do wizualizacji szachownicy
 */

public class GameService {
    private static GameService gameService = null;

    /**
     * Klasyczny singleton
     * @return instancja obiektu
     */
    public static GameService getInstance(){
        if(gameService == null)
            gameService = new GameService();

        return gameService;
    }

    private GameService() {
        board = new Board();
    }

    @Getter
    private Board board;
    @Getter @Setter
    private int moveX;
    @Getter @Setter
    private int moveY;
    public static final String COLOR = "WHITE";

    public void move(int x, int y) {
        board.changeFiguresPosition(x,y, board.getFiguresPosition()[moveX][moveY]);
        board.changeFiguresPosition(moveX, moveY, '0');
    }

}
