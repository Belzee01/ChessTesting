package sample.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.GameEngine;
import sample.models.*;
import sample.models.Board;
import sample.models.CheckMessage;
import sample.models.Images;
import sample.models.Message;
import sample.services.ChessLogicService;

import javax.swing.text.Style;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Kontroler wyglądu szachownicy
 */
public class BoardOverviewController{
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
                    GameEngine.getInstance().getChessLogicService().setBoard((Board) data);
                    Sounds.getInstance().opponentMove();
                    refreshBoard();
                }
                if(data instanceof Message){
                    GameEngine.getInstance().getCommunicationController().receive((Message)data);
                }
                if(data instanceof CheckMessage){
                    CheckMessage msg=(CheckMessage) data;
                    onCheckedAppear(msg.getCheckedColor());
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
    public void onCheckedAppear(int checkColor){
        GameEngine.getInstance().setCheckState(checkColor);
        int myColor=0;
        if(GameEngine.getInstance().isServerRole()==false){
            myColor=1;
        }
        if(myColor==checkColor){
            onChecked();
        }
        else{
            onEnemyChecked();
        }
    }

    public void onCheckMated(){

    }

    public void onChecked(){
        System.out.println("I'm checked");
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"SZACH");
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);
        alert.setWidth(300);
        alert.show();
    }
    public void onEnemyChecked(){
        System.out.println("Enemy checked");
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


            possibleMoves[gameEngine.getMoveX()][gameEngine.getMoveY()]=false;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Przeciwnik zaproponował remis.\nZgadzasz się?");
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);

        ButtonType YES = new ButtonType("Tak");
        ButtonType NO = new ButtonType("Nie");

        alert.getButtonTypes().setAll(YES,NO);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == YES){
            acceptDraw();
        } else if(result.get() == NO){
            declineDraw();
        }
    }

    public void showDrawAnswer(boolean accepted){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(accepted==true)
            alert.setContentText("Przeciwnik zaakceptował remis.");
        else
            alert.setContentText("Przeciwnik odrzucił remis.");
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);

        alert.showAndWait();

        if(accepted==true)
            makeDraw();
    }

    public void showResignationMessage(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("PRZECIWNIK ZREZYGNOWAŁ - WYGRAŁEŚ");
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);

        ButtonType back = new ButtonType("Wróć do menu");
        ButtonType show = new ButtonType("Pokaż historię ruchów");

        alert.getButtonTypes().setAll(back,show);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == back){
            backToMenu();
        } else if(result.get() == show){
            showMovesHistory();
        }

    }


    public void acceptDraw(){
        DrawAnswer answer = new DrawAnswer(true);
        GameEngine.getInstance().getTcpConnectionService().sendObject(answer);
        makeDraw();
    }

    public void declineDraw(){
        DrawAnswer answer = new DrawAnswer(false);
        GameEngine.getInstance().getTcpConnectionService().sendObject(answer);
    }

    public void makeDraw(){
        /* KONIEC GRY - REMIS */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("KONIEC GRY - REMIS");
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);

        ButtonType back = new ButtonType("Wróć do menu");
        ButtonType show = new ButtonType("Pokaż historię ruchów");

        alert.getButtonTypes().setAll(back,show);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == back){
            backToMenu();
        } else if(result.get() == show){
            showMovesHistory();
        }

    }

    public void backToMenu(){
        Stage stage = (Stage) GameEngine.getInstance().getCommunicationController().getResignButton().getScene().getWindow();
        AnchorPane anchorPane = new AnchorPane();
        try{
            anchorPane = FXMLLoader.load(getClass().getResource("../view/newGame.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }

        Scene scene = StyleCss.getInstance().getScene(anchorPane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public void showMovesHistory(){}

}
