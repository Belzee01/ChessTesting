package test;

import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import sample.models.Board;
import sample.services.ChessLogicService;

import static org.junit.Assert.*;

@SuppressWarnings("Duplicates")
public class ChessLogicServiceTest {

    private String boardInitializer = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
            + "        " + "PPPPPPPP" + "RNBQKBNR";

    private Board board;

    private ChessLogicService logicService;

    @Before
    public void setUp() throws Exception {
        board = new Board(boardInitializer, false);
        logicService = new ChessLogicService(board);
    }

    @Test
    public void testChessLogicConstructor(){
        assertNotEquals(new ChessLogicService().getBoard(), null);
        assertNotEquals(new ChessLogicService(board).getBoard(), null);
        Board board1 = null;
        assertEquals(new ChessLogicService(board1).getBoard(), null);
        Board board2 = new Board(boardInitializer, false);
        assertEquals(new ChessLogicService(board2).getBoard(), board2);
    }

    @Test
    public void testGameOver() throws Exception {
        String boardInitializer = "rnbq bnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQ BNR";

        Board board1 = new Board();
        board1.setBoard(boardInitializer);
        logicService.setBoard(board1);

        assertEquals(logicService.gameOver(), true);

        logicService.setBoard(board);

        assertEquals(logicService.gameOver(), false);
    }

    @Test
    public void testMoveWithStandardInput() throws Exception {

        String boardInitializer = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        String newBoard = "rnbqkbnr" + " ppppppp" + "p       " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        int x1 = 0;
        int y1 = 6;
        int x2 = 0;
        int y2 = 5;

        assertEquals(logicService.move(boardInitializer, x1, y1, x2, y2), newBoard);
    }

    @Test
    public void testMoveMethodWithEdgeValues_LessThanZeroInitialValues(){
        /**
         * Error within method move, if initial xy are less than 0, method will acquire position of figure as 0
         * It should throw exception IllegalArgument Exception
         */
        int x1a = -1;
        int y1a = -1;
        int x2a = 0;
        int y2a = 5;

        String boardInitializer2 = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        String newBoard2         = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        assertEquals(logicService.move(boardInitializer2, x1a, y1a, x2a, y2a), newBoard2);
    }

    @Test
    public void testMoveMethodWithEdgeValues_GreaterThanNineInitialValues(){
        /**
         * Error within method move, if initial xy are greater than 7, method will acquire position of figure as 0
         * It should throw exception IllegalArgument Exception
         */
        int x1b = 8;
        int y1b = 8;
        int x2b = 0;
        int y2b = 5;

        String boardInitializer3 = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        String newBoard3         = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        assertEquals(logicService.move(boardInitializer3, x1b, y1b, x2b, y2b), newBoard3);
    }

    @Test
    public void testMoveMethodWithEdgeValues_LessThanZeroSecondValues(){
        /**
         * Error within method move, if second xy are less than 0, method will move figure to position 0
         * It should throw exception IllegalArgument Exception
         */
        int x1b = 0;
        int y1b = 6;
        int x2b = -2;
        int y2b = -1;

        String boardInitializer1 = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        String newBoard1         = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        assertEquals(logicService.move(boardInitializer1, x1b, y1b, x2b, y2b), newBoard1);
    }

    @Test
    public void testMoveMethodWithEdgeValues_GreaterThanSevenSecondValues(){
        /**
         * Error within method move, if second xy are greater than 7, method will move figure to position 0
         * It should throw exception IllegalArgument Exception
         */
        int x1b = 0;
        int y1b = 6;
        int x2b = 9;
        int y2b = 8;

        String boardInitializer1 = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        String newBoard1         = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        assertEquals(logicService.move(boardInitializer1, x1b, y1b, x2b, y2b), newBoard1);
    }

    @Test
    public void testMoveMethodWithNullValue(){
        /**
         * Method doesn't check if object arguments aren't null
         * Make if statement and throw IllegalArgumentException when null is given as argument
         */

        int x1b = 0;
        int y1b = 6;
        int x2b = 0;
        int y2b = 5;

        String boardInitializer1 = null;

        String newBoard1         = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        assertEquals(logicService.move(boardInitializer1, x1b, y1b, x2b, y2b), newBoard1);
    }

    @Test
    public void testMoveMethodWithNotProperInput(){
        /**
         * Method does not check if given Strings are in proper format
         * Throw IllegalArgumentException
         */
        int x1b = 0;
        int y1b = 6;
        int x2b = 0;
        int y2b = 5;

        String boardInitializer1 = new String("");

        String newBoard1         = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        assertEquals(logicService.move(boardInitializer1, x1b, y1b, x2b, y2b), newBoard1);
    }

    @Test
    public void testGetPossibleMovesMethod_IfThereIsValidPossibleMove() throws Exception{
        String newBoard1         = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        String newBoard2 = "rnbqkbnr" + " ppppppp" + "p       " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";

        for (String s:logicService.getPossibleMoves(newBoard1, false)) {
            if(s.equals(newBoard2)){
                assertEquals(s, newBoard2); break;
            }
        }
    }

    @Test
    public void testGetPossibleMovesMethodWithNullValue(){
        /**
         * Error, method does not check if given String is not null
         * Throw IllegalArgumentException
         */

        String newBoard1 = null;

        logicService.getPossibleMoves(newBoard1, false);
    }

    @Test
    public void testGetPossibleMovesMethodWithEmptyBoard(){
        String newBoard1         = "        " + "        " + "        " + "        " + "        "
                + "        " + "        " + "        ";

        String[] emptyArray = {};

        assertArrayEquals(emptyArray, logicService.getPossibleMoves(newBoard1, false));
    }

    @Test
    public void testGetPossibleMovesMethodWithInvalidBoard(){
        String newBoard1         = "llllllll" + "        " + "        " + "        " + "        "
                + "        " + "        " + "zzzzzzzz";

        String[] emptyArray = {};

        assertArrayEquals(emptyArray, logicService.getPossibleMoves(newBoard1, false));
    }

    @Test
    public void testGetPossibleMovesMethodWithOneFigureOnBoard(){
        String newBoard1         = "p       " + "        " + "        " + "        " + "        "
                + "        " + "        " + "        ";

        String[] emptyArray = {"        p                                                       "};

        assertArrayEquals(emptyArray, logicService.getPossibleMoves(newBoard1, false));
    }

    @Test
    public void testGetPossibleMovesMethodWithInvalidFormatOfBoard(){
        /**
         * "StringIndexOutOfBoundsException: String index out of range: 56"
         * Method should check if format of given String is correct
         * Throw IllegalArgumentException
         */

        String newBoard1         = "p       " + "        " + "        " + "        " + "        "
                + "        " + "        ";

        String[] emptyArray = {"        p                                               "};

        assertArrayEquals(emptyArray, logicService.getPossibleMoves(newBoard1, false));
    }

    @Test
    public void testGetPossibleMovesArray() throws Exception {
        String newBoard1         = "        " + "p       " + "        " + "        " + "        "
                + "        " + "        " + "        ";

        boolean[][] arrayBoard = {{false,false,false,false,false,false,false, false},
                {false,false,false,false,false,false,false,false},
                {true,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false}};

        logicService.setBoard(new Board(newBoard1, false));
        assertArrayEquals(logicService.getPossibleMovesArray(0, 7), arrayBoard);
    }

    @Test
    public void testGetFiguresArray() throws Exception {

    }

    @Test
    public void testGetCheck() throws Exception {

    }

    @Test
    public void testSetBoard() throws Exception {

    }

    @Test
    public void testGetBoard() throws Exception {

    }
}