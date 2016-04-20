package sample.models;


import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import sample.GameEngine;
import sample.services.GameService;


/**
 * Klasa reprezentująca obrazy figur i podświetleń możliwych ruchów.
 */
public class Images {

    private Board board =  GameService.getInstance().getBoard();

    /**
     * Zwracanie odpowiedniego obiektu Image
     * @param index znak odpowiadający figurze
     * @return obiekt klasy Image
     */
    public Image getFigureImage(char index) {
        switch(index) {
            case 'K': return K;
            case 'k': return k;
            case 'Q': return Q;
            case 'q': return q;
            case 'B': return B;
            case 'b': return b;
            case 'R': return R;
            case 'r': return r;
            case 'N': return N;
            case 'n': return n;
            case 'P': return P;
            case 'p': return p;
        }
        return null;
    }

    /**
     * Pobranie obrazu dla odpowiedniej pozycji (jeśli jest figura pole bicia, jeśli nie pole ruchu)
     * @param positionX pozycja x pola
     * @param positionY pozycja y pola
     * @return obiekt Image
     */
    public Image getMoveImage(int positionX, int positionY) {
        char [][] figuresPosition = GameEngine.getInstance().getChessLogicService().getFiguresArray();
        if(figuresPosition[positionX][positionY] == ' ')
            return MOVE;
        return CAPTURE;
    }

    private final Image K = new Image(Images.class.getResourceAsStream("../view/img/K.png"));
    private final Image k = new Image(Images.class.getResourceAsStream("../view/img/k.png"));
    private final Image Q = new Image(Images.class.getResourceAsStream("../view/img/Q.png"));
    private final Image q = new Image(Images.class.getResourceAsStream("../view/img/q.png"));
    private final Image B = new Image(Images.class.getResourceAsStream("../view/img/B.png"));
    private final Image b = new Image(Images.class.getResourceAsStream("../view/img/b.png"));
    private final Image R = new Image(Images.class.getResourceAsStream("../view/img/R.png"));
    private final Image r = new Image(Images.class.getResourceAsStream("../view/img/r.png"));
    private final Image N = new Image(Images.class.getResourceAsStream("../view/img/N.png"));
    private final Image n = new Image(Images.class.getResourceAsStream("../view/img/n.png"));
    private final Image P = new Image(Images.class.getResourceAsStream("../view/img/P.png"));
    private final Image p = new Image(Images.class.getResourceAsStream("../view/img/p.png"));

    private final Image MOVE = new Image(Images.class.getResourceAsStream("../view/img/move.png"));
    private final Image CAPTURE = new Image(Images.class.getResourceAsStream("../view/img/capture.png"));
}
