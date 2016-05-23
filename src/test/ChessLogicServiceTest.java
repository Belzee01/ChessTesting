package test;

import org.junit.Before;
import org.junit.Test;
import sample.models.Board;
import sample.services.ChessLogicService;

import static org.junit.Assert.*;

public class ChessLogicServiceTest {

    private ChessLogicService chessLogicService;
    private String testBoard = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
            + "        " + "PPPPPPPP" + "RNBQKBNR";
    private Board board = new Board();
    @Before
    public void setUp() throws Exception {
        chessLogicService = new ChessLogicService();
    }

    ////Usage of move method
    @Test
    public void testGetNewBoard() throws Exception {
//        String move = " ";
//        String newBoard = chessLogicService.getNewBoard(board, move);
    }

    @Test
    public void testMove() throws Exception {
        //String newMove = chessLogicService.move(board, );
    }

    @Test
    public void testPos() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testGetPossibleMoves() throws Exception {

    }

    @Test
    public void testGetPossibleMovesArray() throws Exception {

    }

    @Test
    public void testGetFiguresArray() throws Exception {

    }

    @Test
    public void testIsWhite() throws Exception {

    }

    @Test
    public void testIsBlack() throws Exception {

    }

    @Test
    public void testIsPawn() throws Exception {

    }

    @Test
    public void testIsRook() throws Exception {

    }

    @Test
    public void testIsKnight() throws Exception {

    }

    @Test
    public void testIsBishop() throws Exception {

    }

    @Test
    public void testIsQueen() throws Exception {

    }

    @Test
    public void testIsKing() throws Exception {

    }

    @Test
    public void testIsEmpty() throws Exception {

    }

    @Test
    public void testIsDifferentColor() throws Exception {

    }

    @Test
    public void testGetPossibleMovesPawn() throws Exception {

    }

    @Test
    public void testGetPossibleMovesRook() throws Exception {

    }

    @Test
    public void testGetPossibleMovesBishop() throws Exception {

    }

    @Test
    public void testGetPossibleMovesKnight() throws Exception {

    }

    @Test
    public void testGetPossibleMovesQueen() throws Exception {

    }

    @Test
    public void testGetPossibleMovesKing() throws Exception {

    }

    @Test
    public void testGetPossibleMoves1() throws Exception {

    }

    @Test
    public void testBlackMove() throws Exception {

    }

    @Test
    public void testValue() throws Exception {

    }

    @Test
    public void testEvalBest() throws Exception {

    }

    @Test
    public void testEval() throws Exception {

    }

    @Test
    public void testEval1() throws Exception {

    }

    @Test
    public void testSetBoard() throws Exception {

    }

    @Test
    public void testGetBoard() throws Exception {

    }
}