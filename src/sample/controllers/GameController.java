package sample.controllers;

/**
 * Symulacja GameEngine do wizualizacji szachownicy
 */

public class GameController {
    public static final String COLOR = "WHITE";

    public static void setMoves() {
        possibleMoves = new boolean[8][8];
        for(int i =0; i<8; ++i)
            for(int j=1; j<6; ++j) {
                possibleMoves[i][j] = true;
            }
    }

    public static void setFigures() {
        figuresPosition = new char[8][8];
        for(int i=0; i<8; ++i)
            for(int j=0; j<8; ++j)
                figuresPosition[i][j]=0;
        figuresPosition[0][0] = figuresPosition[0][7] = 'R';
        figuresPosition[0][1] = figuresPosition[0][6] = 'N';
        figuresPosition[0][2] = figuresPosition[0][5] = 'B';
        figuresPosition[0][3] = 'Q';
        figuresPosition[0][4] = 'K';
        figuresPosition[7][0] = figuresPosition[7][7] = 'r';
        figuresPosition[7][1] = figuresPosition[7][6] = 'n';
        figuresPosition[7][2] = figuresPosition[7][5] = 'b';
        figuresPosition[7][3] = 'q';
        figuresPosition[7][4] = 'k';
        for(int i=0; i<8; ++i) {
            figuresPosition[1][i] = 'P';
            figuresPosition[6][i] = 'p';
        }
    }

    public static boolean[][] getPossibleMoves() {
        return possibleMoves;
    }
    public static char[][] getFiguresPosition() {
        return figuresPosition;
    }

    public static void setMove(int x, int y) {
        moveX = x;
        moveY = y;
    }

    public static void move(int x, int y) {
        figuresPosition[x][y] = figuresPosition[moveX][moveY];
        figuresPosition[moveX][moveY] = 0;
    }

    private static boolean [][] possibleMoves;
    private static char [][] figuresPosition;
    private static int moveX;
    private static int moveY;

}
