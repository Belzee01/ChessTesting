package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.GameEngine;
import sample.models.*;
import sample.services.ChessLogicService;

import javax.swing.text.Style;
import java.io.IOException;


/**
 * Kontroler wyglądu szachownicy
 */
public class BoardOverviewController {
    @FXML
    private GridPane gridPane;
    private GameEngine gameEngine = GameEngine.getInstance();
    private Images images = new Images();


    private String evenColor;
    private String oddColor;

    public BoardOverviewController() {
        GameEngine.getInstance().setChessLogicService(new ChessLogicService(new Board()));

        GameEngine.getInstance().getTcpConnectionService().setOnReceiveNewData(data -> {
            Platform.runLater(() ->{
                if (data instanceof Board) {


                    System.out.println("Received new data \n :" + ((Board) data).getBoard());
                    GameEngine.getInstance().getChessLogicService().setBoard((Board) data);
                    refreshBoard();
                    //GameEngine.getInstance().getTcpConnectionService().sendObject((Board)data);


                }
                if(data instanceof Message){
                    GameEngine.getInstance().getCommunicationController().receive((Message)data);
                }
                if(data instanceof DrawRequest){
                    showDrawRequest();
                }
                if(data instanceof DrawAnswer){
                    DrawAnswer answer = (DrawAnswer)data;
                    if(answer.isAccepted())
                        showDrawAnswer(true);
                    else
                        showDrawAnswer(false);
                }
                if(data instanceof ResignationMessage){
                    showResignationMessage();
                }


            });
        });

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BoardOverviewController.class.getResource("../view/ChatWindow.fxml"));

    }

    /**
     * Inicjalizacja koloru pól szachownicy
     */
    public void initBoard() {
        final String CSS = "-fx-background-color: ";
        final String SEMICOLON = ";";
        this.evenColor = CSS + "#fffdca" + SEMICOLON;
        this.oddColor = CSS + "#a58240" + SEMICOLON;
        refreshBoard();
    }

    /**
     * Odświeżenie szachownicy (kolorownie pól, wywołanie metody drawFigures)
     */
    private void refreshBoard() {
        gridPane.getChildren().clear();

        for(int i=0; i<8; ++i)
            for(int j=0; j<8; ++j) {

                Pane pane = new Pane();
                pane.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> refreshBoard() );

                if(i%2==0) {
                    if(j%2==0) pane.setStyle(evenColor);
                    else pane.setStyle(oddColor);
                } else {
                    if(j%2==0) pane.setStyle(oddColor);
                    else pane.setStyle(evenColor);
                }

                gridPane.add(pane, j, i);
            }
        drawFigures();
    }

    /**
     * Rysowanie figur i dodawanie eventów.
     */
    private void drawFigures() {
        char [][] figuresPosition = gameEngine.getChessLogicService().getFiguresArray();

        for(int i=0; i<8; ++i)
            for(int j=0; j<8; ++j) {
                if (figuresPosition[j][i]==0)
                    continue;
                ImageView iv = new ImageView(images.getFigureImage(figuresPosition[j][i]));
                iv.fitWidthProperty().bind(gridPane.widthProperty().divide(8));
                iv.fitHeightProperty().bind(gridPane.heightProperty().divide(8));

                if(GameEngine.getInstance().isServerRole()) {
                    if (figuresPosition[j][i] > 'a' && figuresPosition[j][i] < 'z') {
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> showMoves(iv));
                    }
                    else {
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> refreshBoard());
                    }
                } else {
                    if (figuresPosition[j][i] > 'A' && figuresPosition[j][i] < 'Z') {
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> showMoves(iv));
                    }
                    else {
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> refreshBoard());
                    }
                }

                gridPane.add(iv, i, j);
            }
    }

    /**
     * Handler wyświetlający możliwe ruchy dla wybranaj figury
     * @param IV obiekt klasy ImageView dla którego sprawdzane są możliwe ruchy
     */
    private void showMoves(ImageView IV) {
        if(gameEngine.isServerRole()==gameEngine.getChessLogicService().getBoard().getServerTurn()) {
            refreshBoard();

            gameEngine.setMoveX(GridPane.getColumnIndex(IV));
            gameEngine.setMoveY(GridPane.getRowIndex(IV));
            boolean[][] possibleMoves = gameEngine.getChessLogicService().getPossibleMovesArray(GridPane.getColumnIndex(IV), GridPane.getRowIndex(IV));


            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (possibleMoves[i][j]) {
                        ImageView iv = new ImageView(images.getMoveImage(j, i));
                        iv.fitWidthProperty().bind(gridPane.widthProperty().divide(8));
                        iv.fitHeightProperty().bind(gridPane.heightProperty().divide(8));
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> move(iv));

                        gridPane.add(iv, i, j);
                    }
                }
            }
        }
    }

    /**
     * Handler odpowiadający za dokonywanie ruchu po kliknięciu w wybrany z możliwych ruchów dla figury wywołującej event w miejsce możliwego ruchu iv
     * @param iv obiekt klasy ImageView miejsce w które zostaje przesunięta figura dla której metoda zostaje wywołana
     */
    private void move(ImageView iv) {
        if(gameEngine.isServerRole()==gameEngine.getChessLogicService().getBoard().getServerTurn()) {
            gameEngine.move(GridPane.getColumnIndex(iv), GridPane.getRowIndex(iv));
            refreshBoard();
        }
    }


    public void showDrawRequest(){
        Stage parentStage = (Stage)gridPane.getScene().getWindow();
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        try{
            root = FXMLLoader.load(getClass().getResource("../view/DrawRequest.fxml"));
        }catch (IOException e) {
            e.printStackTrace();
        }


        Scene scene = StyleCss.getInstance().getScene(root, 300, 300);
        stage.setTitle("Prośba o remis");
        stage.setScene(scene);
        stage.show();
    }

    public void showDrawAnswer(boolean accepted){
        Stage parentStage = (Stage)gridPane.getScene().getWindow();
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        try{
            if(accepted)
                root = FXMLLoader.load(getClass().getResource("../view/DrawPositiveAnswer.fxml"));
            else
                root = FXMLLoader.load(getClass().getResource("../view/DrawNegativeAnswer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = StyleCss.getInstance().getScene(root, 300, 300);
        stage.setTitle("Odpowiedź");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public void showResignationMessage(){
        Stage parentStage = (Stage)gridPane.getScene().getWindow();
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        try{
            anchorPane = FXMLLoader.load(getClass().getResource("../view/Resignation.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }

        Scene scene = StyleCss.getInstance().getScene(anchorPane, 300, 300);
        stage.setTitle("Rezygnacja");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
