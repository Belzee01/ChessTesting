package sample.services;

import com.sun.javaws.exceptions.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.omg.CORBA.DynAnyPackage.Invalid;
import sample.GameEngine;
import sample.models.Board;

import java.util.*;


@AllArgsConstructor
public class ChessLogicService {

    @Getter @Setter
    private Board board;

    public ChessLogicService(){
        if(board == null)
            board = new Board();
    }
    
    public boolean gameOver() {
        return board.getBoard().indexOf("k") == -1 || board.getBoard().indexOf("K") == -1;
    }

    /*
    return 1 if black won, 0 if white, -1 if none
     */
    public int getWinner(){
        if (board.getBoard().indexOf("k")==-1){
            return 0;
        }
        else if(board.getBoard().indexOf("K")==-1){
            return 1;
        }
        return -1;
    }

    public String move(String board, int x1, int y1, int x2, int y2) {
        if(!BoardValidator.isBoardStringValid(board) ||
                x1<0 || x1>=8 ||
                x2<0 || x2>=8 ||
                y1<0 || y1>=8 ||
                y2<0 || y2>=8
                ){
                throw new IllegalArgumentException();
        }
        String piece = get(board, x1, y1);
        board = set(board, x1, y1, " ");
        board = set(board, x2, y2, piece);
        return board;
    }

    private int pos(int x1, int y1) {
        if (x1 < 0 || x1 > 7 || y1 < 0 || y1 > 7) {
            return 0;
        }
        else
            return x1 + (7 - y1) * 8;
    }

    public String get(String board, int x1, int y1) {
        if (x1 < 0 || x1 > 7 || y1 < 0 || y1 > 7)
            return "X";

        int pos=pos(x1, y1);
        return "" + board.charAt(pos);
    }

    private String set(String board, int x1, int y1, String piece) {
        int pos = pos(x1, y1);
        return board.substring(0, pos) + piece + board.substring(pos + 1);
    }

    public String[] getPossibleMoves(String board, boolean white) {
        if(!BoardValidator.isBoardStringValid(board)){
            throw new IllegalArgumentException();
        }
        List result = new ArrayList();
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++) {
                String piece = get(board, x, y);
                if(isWhite(piece)==white) {
                    String[] boards = getPossibleMoves(board, x, y);
                    result.addAll(Arrays.asList(boards));
                }
            }
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    /*
    Zwraca tablicę z zaznaczonymi polami na które figula z pola o współrzędnych x,y może się poruszyć
     */
    public boolean[][] getPossibleMovesArray(int x,int y){
        if (x < 0 || x > 7 || y < 0 || y> 7){
            throw new IllegalArgumentException();
        }


        boolean  maskArray[][] =new boolean[8][8];
        String oldBoard=board.getBoard();
        String possibleMoves[]=getPossibleMoves(oldBoard,x,y);

        String foo=get(oldBoard,x,y);
        System.out.println(x+":"+y);

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                maskArray[i][j]=false;
                for(int k=0;k<possibleMoves.length;k++){
                    if(!get(oldBoard,i,j).equals(get(possibleMoves[k],i,j))) {
                        maskArray[i][j]=true;
                    }
                }
            }
        }
        return maskArray;
    }

    /*
     Przeprowadza konwersję z zapisu szachownicy w postaci Stringa na
     tablicę charów wdł reguły :

     ABCDEFGHI

     T[0][0]='A',T[1][0]='B', T[2][0]='C' ...
     T[0][1]='D',T[1][1]='E', T[2][1]='F' ...
     T[0][2]='G',T[1][2]='H', T[2][2]='I' ...
     ...
     ...
     ...

      */
    public char[][] getFiguresArray(){
        char [][] figuresArray=new char[8][8];

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                figuresArray[i][j]=get(board.getBoard(),j,i).charAt(0);
            }
        }
        return figuresArray;
    }

    private boolean isWhite(String piece) {
        return "PRNBQK".indexOf(piece) != -1;
    }

    private boolean isBlack(String piece) {
        return "prnbqk".indexOf(piece) != -1;
    }

    private boolean isPawn(String piece) {
        return "Pp".indexOf(piece) != -1;
    }

    private boolean isRook(String piece) {
        return "Rr".indexOf(piece) != -1;
    }

    private boolean isKnight(String piece) {
        return "Nn".indexOf(piece) != -1;
    }

    private boolean isBishop(String piece) {
        return "Bb".indexOf(piece) != -1;
    }

    private boolean isQueen(String piece) {
        return "Qq".indexOf(piece) != -1;
    }

    private boolean isKing(String piece) {
        return "Kk".indexOf(piece) != -1;
    }

    private boolean isEmpty(String piece) {
        return piece.equals(" ");
    }

    private boolean isDifferentColor(String piece, String other) {
        return (isWhite(piece) && isBlack(other))
                || (isBlack(piece) && isWhite(other));
    }

    /**
     * Return -1 if no check detected , 0 if white check, 1 if black check
     * @return
     */
    public int getCheck(){
        String [] availableMoves=getPossibleMoves(board.getBoard(),true);
        String [] secondAvailableMoves=getPossibleMoves(board.getBoard(),false);

        for(int i=0;i<availableMoves.length;i++){
            if(availableMoves[i].indexOf("K")==-1 ){
                return 1;
            }
            else if(availableMoves[i].indexOf("k")==-1){
                return 0;
            }
        }

        for(int i=0;i<secondAvailableMoves.length;i++){
            if(secondAvailableMoves[i].indexOf("K")==-1 ){
                return 1;
            }
            else if(secondAvailableMoves[i].indexOf("k")==-1){
                return 0;
            }
        }

        return -1;
    }

    public boolean getMat(){
        System.out.println("MAT DETECTION");

        String [] availableMoves=getPossibleMoves(board.getBoard(), GameEngine.getInstance().isServerRole());

        System.out.println(availableMoves.length);
        for(int i=0;i<availableMoves.length;i++){
            String [] nextIteration=getPossibleMoves(availableMoves[i],!GameEngine.getInstance().isServerRole());
            boolean ableToCheck=false;
            for(int j=0;j<nextIteration.length;j++){
                if(nextIteration[j].indexOf("K")==-1 || nextIteration[j].indexOf("k")==-1){
                    System.out.println("CHECK EXIST");
                    ableToCheck=true;
                    break;
                }
            }
            if(ableToCheck==false){
                for(int k=0;k<8;k++){
                    for(int j=0;j<8;j++){
                        System.out.print(this.get(availableMoves[i],j,k));
                    }
                    System.out.println();
                }

                return false;
            }
        }
        System.out.println("CH M DET");
        return true;
    }



    ///////////////// Walidator ruchow
    private String[] getPossibleMovesPawn(String board, int x, int y) {
        List result = new ArrayList();
        if (isWhite(get(board, x, y))) {
            if (isEmpty(get(board, x, y + 1))) {
                result.add(move(board, x, y, x, y + 1));
                if (y == 1)
                    if (isEmpty(get(board, x, y + 2))) {
                        result.add(move(board, x, y, x, y + 2));
                    }
            }
            if (isBlack(get(board, x - 1, y + 1)))
                result.add(move(board, x, y, x - 1, y + 1));
            if (isBlack(get(board, x + 1, y + 1)))
                result.add(move(board, x, y, x + 1, y + 1));
        } else {
            if (isEmpty(get(board, x, y - 1))) {
                result.add(move(board, x, y, x, y - 1));
                if (y == 6)
                    if (isEmpty(get(board, x, y - 2))) {
                        result.add(move(board, x, y, x, y - 2));
                    }
            }
            if (isWhite(get(board, x - 1, y - 1)))
                result.add(move(board, x, y, x - 1, y - 1));
            if (isWhite(get(board, x + 1, y - 1)))
                result.add(move(board, x, y, x + 1, y - 1));
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    private String[] getPossibleMovesRook(String board, int x, int y) {
        List result = new ArrayList();
        String piece = get(board, x, y);
        for (int i = 1; i <= 7; i++) {
            String destPiece = get(board, x - i, y);
            boolean isEmpty = isEmpty(destPiece);
            if (isEmpty || isDifferentColor(piece, destPiece)) {
                result.add(move(board, x, y, x - i, y));
            }
            if (!isEmpty)
                break;
        }
        for (int i = 1; i <= 7; i++) {
            String destPiece = get(board, x + i, y);
            boolean isEmpty = isEmpty(destPiece);
            if (isEmpty || isDifferentColor(piece, destPiece)) {
                result.add(move(board, x, y, x + i, y));
            }
            if (!isEmpty)
                break;
        }
        for (int i = 1; i <= 7; i++) {
            String destPiece = get(board, x, y - i);
            boolean isEmpty = isEmpty(destPiece);
            if (isEmpty || isDifferentColor(piece, destPiece)) {
                result.add(move(board, x, y, x, y - i));
            }
            if (!isEmpty)
                break;
        }
        for (int i = 1; i <= 7; i++) {
            String destPiece = get(board, x, y + i);
            boolean isEmpty = isEmpty(destPiece);
            if (isEmpty || isDifferentColor(piece, destPiece)) {
                result.add(move(board, x, y, x, y + i));
            }
            if (!isEmpty)
                break;
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    private String[] getPossibleMovesBishop(String board, int x, int y) {
        List result = new ArrayList();
        String piece = get(board, x, y);
        for (int i = 1; i <= 7; i++) {
            String destPiece = get(board, x - i, y - i);
            boolean isEmpty = isEmpty(destPiece);
            if (isEmpty || isDifferentColor(piece, destPiece)) {
                result.add(move(board, x, y, x - i, y - i));
            }
            if (!isEmpty)
                break;
        }
        for (int i = 1; i <= 7; i++) {
            String destPiece = get(board, x - i, y + i);
            boolean isEmpty = isEmpty(destPiece);
            if (isEmpty || isDifferentColor(piece, destPiece)) {
                result.add(move(board, x, y, x - i, y + i));
            }
            if (!isEmpty)
                break;
        }
        for (int i = 1; i <= 7; i++) {
            String destPiece = get(board, x + i, y + i);
            boolean isEmpty = isEmpty(destPiece);
            if (isEmpty || isDifferentColor(piece, destPiece)) {
                result.add(move(board, x, y, x + i, y + i));
            }
            if (!isEmpty)
                break;
        }
        for (int i = 1; i <= 7; i++) {
            String destPiece = get(board, x + i, y - i);
            boolean isEmpty = isEmpty(destPiece);
            if (isEmpty || isDifferentColor(piece, destPiece)) {
                result.add(move(board, x, y, x + i, y - i));
            }
            if (!isEmpty)
                break;
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    private String[] getPossibleMovesKnight(String board, int x, int y) {
        List result = new ArrayList();
        String piece = get(board, x, y);
        int[][] moves = { { x - 1, y + 2 }, { x + 1, y + 2 }, { x - 2, y + 1 },
                { x + 2, y + 1 }, { x - 2, y - 1 }, { x + 2, y - 1 }, { x - 1, y - 2 },
                { x + 1, y - 2 } };
        for (int i = 0; i < moves.length; i++) {
            String destPiece = get(board, moves[i][0], moves[i][1]);
            if (isEmpty(destPiece) || isDifferentColor(piece, destPiece))
                result.add(move(board, x, y, moves[i][0], moves[i][1]));
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    private String[] getPossibleMovesQueen(String board, int x, int y) {
        String[] movesBishop = getPossibleMovesBishop(board, x, y);
        String[] movesRook = getPossibleMovesRook(board, x, y);
        String[] result = new String[movesBishop.length + movesRook.length];
        System.arraycopy(movesBishop, 0, result, 0, movesBishop.length);
        System
                .arraycopy(movesRook, 0, result, movesBishop.length, movesRook.length);
        return result;
    }

    private String[] getPossibleMovesKing(String board, int x, int y) {
        List result = new ArrayList();
        String piece = get(board, x, y);
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                String destPiece = get(board, x + i, y + j);
                if (isEmpty(destPiece) || isDifferentColor(piece, destPiece))
                    result.add(move(board, x, y, x + i, y + j));
            }
        return (String[]) result.toArray(new String[result.size()]);
    }

    /*
        Wyszukuje możliwe do wykonania ruchy z pola o zadanych współrzędnych
        Zwraca listę stringów z których każdy reprezentuje stan szachownicy po wykonaniu jednego z dostępnych ruchów
     */
    public String[] getPossibleMoves(String board, int x, int y) {
        if(!BoardValidator.isBoardStringValid(board)){
            throw new IllegalArgumentException();
        }
        String piece = get(board, x, y);
        if (isPawn(piece))
            return getPossibleMovesPawn(board, x, y);
        if (isRook(piece))
            return getPossibleMovesRook(board, x, y);
        if (isBishop(piece))
            return getPossibleMovesBishop(board, x, y);
        if (isKnight(piece))
            return getPossibleMovesKnight(board, x, y);
        if (isQueen(piece))
            return getPossibleMovesQueen(board, x, y);
        if (isKing(piece))
            return getPossibleMovesKing(board, x, y);
        return new String[0];
    }
}
