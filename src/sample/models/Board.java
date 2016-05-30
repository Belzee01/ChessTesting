package sample.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sample.services.BoardValidator;

import java.io.Serializable;

/**
 * Klasa reprezentująca szachownicę.
 * Przechowuje tablice aktualnego położenia figur oraz możliwych ruchów dla danej figury
 */

public class Board implements Serializable{
    @Getter @Setter
    private String board;

    @Getter @Setter
    private Boolean serverTurn;

    public Board(String newBoard,boolean newServerTurn){
        if(!BoardValidator.isBoardStringValid(newBoard)){
            throw new IllegalArgumentException();
        }
        else{

            board=newBoard;
            serverTurn=newServerTurn;
        }
    }

    public Board(){
        String boardInitializer = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";
        setBoard(boardInitializer);
        serverTurn=true;
    }
}
