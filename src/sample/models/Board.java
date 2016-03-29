package sample.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Klasa reprezentująca szachownicę.
 * Przechowuje tablice aktualnego położenia figur oraz możliwych ruchów dla danej figury
 */
@AllArgsConstructor
public class Board {
    @Getter @Setter
    private String board;

    @Getter @Setter
    private boolean [][] possibleMoves;

    @Getter
    private char [][] figuresPosition;


    public Board(){
        board = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";
        figuresPosition = new char[8][8];
        for(int i=0; i<8; i++)
            for(int j=0; j<8; j++)
                figuresPosition[i][j] = this.board.charAt(8*i+j);

        possibleMoves = new boolean[8][8];
        setPossibleMoves();
    }



    public void setPossibleMoves() {
        possibleMoves = new boolean[8][8];
        for(int i =0; i<8; ++i)
            for(int j=1; j<6; ++j) {
                possibleMoves[i][j] = true;
            }
    }


    public void changeFiguresPosition(int x, int y, char z){
        figuresPosition[x][y] = z;
    }
}
