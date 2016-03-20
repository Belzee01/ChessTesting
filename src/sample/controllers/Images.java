package sample.controllers;


import javafx.scene.image.Image;

public class Images {
    public static Image getFigureImage(char index) {
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

    public static Image getMoveImage(int positionX, int positionY) {
        char [][] figuresPosition = GameController.getFiguresPosition();
        if(figuresPosition[positionX][positionY] == 0)
            return MOVE;
        return CAPTURE;
    }

    private static final Image K = new Image(Images.class.getResourceAsStream("../view/img/K.png"));
    private static final Image k = new Image(Images.class.getResourceAsStream("../view/img/k.png"));
    private static final Image Q = new Image(Images.class.getResourceAsStream("../view/img/Q.png"));
    private static final Image q = new Image(Images.class.getResourceAsStream("../view/img/q.png"));
    private static final Image B = new Image(Images.class.getResourceAsStream("../view/img/B.png"));
    private static final Image b = new Image(Images.class.getResourceAsStream("../view/img/b.png"));
    private static final Image R = new Image(Images.class.getResourceAsStream("../view/img/R.png"));
    private static final Image r = new Image(Images.class.getResourceAsStream("../view/img/r.png"));
    private static final Image N = new Image(Images.class.getResourceAsStream("../view/img/N.png"));
    private static final Image n = new Image(Images.class.getResourceAsStream("../view/img/n.png"));
    private static final Image P = new Image(Images.class.getResourceAsStream("../view/img/P.png"));
    private static final Image p = new Image(Images.class.getResourceAsStream("../view/img/p.png"));

    private static final Image MOVE = new Image(Images.class.getResourceAsStream("../view/img/move.png"));
    private static final Image CAPTURE = new Image(Images.class.getResourceAsStream("../view/img/capture.png"));
}
