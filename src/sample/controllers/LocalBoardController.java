package sample.controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.GameEngine;
import sample.models.*;
import sample.services.ChessLogicService;
import sample.services.CounterService;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

public class LocalBoardController {
    @FXML
    private GridPane gridPane;
    private GameEngine gameEngine = GameEngine.getInstance();
    private Images images = new Images();

    private String evenColor;
    private String oddColor;

    @FXML
    private Label whiteTime;

    @FXML
    private Label blackTime;



    public LocalBoardController() {
        GameEngine.getInstance().setCounterService(new CounterService());
        GameEngine.getInstance().setChessLogicService(new ChessLogicService(new Board()));
        gameEngine.setServerRole(true);

        if(GameEngine.getInstance().isServerRole()){
            if(GameEngine.getInstance().getTimeGameMode()==-1){
                GameEngine.getInstance().getCounterService().disableTimeOutMode();
            }
            else{
                GameEngine.getInstance().getCounterService().enableTimeOutMode(Duration.ofMinutes(
                        GameEngine.getInstance().getTimeGameMode()
                ));
            }
            GameEngine.getInstance().getCounterService().stopTiming();
        }


        GameEngine.getInstance().getCounterService().setOnTimeOut(data->{
            Platform.runLater(()->{
                onTimeOut(data);
            });
        });


    }

    public void onEnemyTimeOut(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("KONIEC GRY - PRZECIWNIKOWI SKOŃCZYŁ SIĘ CZAS");
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

    public void onTimeOut(Duration d){
        GameEngine.getInstance().getCounterService().stopTiming();
        GameEngine.getInstance().getTcpConnectionService().sendObject(new TimeOutMessage());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("KONIEC GRY - SKOŃCZYŁ CI SIĘ CZAS");
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


    public void onEnemyCheckMated(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("KONIEC GRY");
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

    public void onCheckMated(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("KONIEC GRY");
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

    public void onChecked(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"SZACH");
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);
        alert.setWidth(300);
        alert.show();
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

    /**
     * Handler odpowiadający za dokonywanie ruchu po kliknięciu w wybrany z możliwych ruchów dla figury wywołującej event w miejsce możliwego ruchu iv
     * @param iv - obiekt klasy ImageView; miejsce w które zostaje przesunięta figura dla której metoda zostaje wywołana
     */
    private void move(ImageView iv) {
        String check = gameEngine.localMove(GridPane.getColumnIndex(iv), GridPane.getRowIndex(iv));

        refreshBoard();

        if(!check.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(check);
            alert.setHeaderText(null);
            alert.setTitle(null);
            alert.setGraphic(null);

            alert.showAndWait();

        }

        if(check.equals("SZACH MAT")){
           endGame(gameEngine.isServerRole() ? "BIAŁE" : "CZARNE");
        }
    }



    /**
     * Metoda wyświetlająca informację, że przeciwnik zrezygnował z gry i kończąca grę.
     */
    public void endGame(String winner){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(winner==null)
            winner = "";
        else
            winner = " - WYGRAŁY "+winner;
        alert.setContentText("KONIEC GRY"+winner);
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);

        ButtonType back = new ButtonType("Wróć do menu");
        ButtonType show = new ButtonType("Historia ruchów");

        alert.getButtonTypes().setAll(back,show);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == back){
            backToMenu();
        } else if(result.get() == show){
            showMovesHistory();
        }

    }


    public void backToMenu(){
        Stage stage = (Stage) gridPane.getScene().getWindow();
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

    /**
     * Metoda wyświetlająca okno z historią ruchów
     */
    public void showMovesHistory(){
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Parent root = new Parent(){};
        try{
            root = FXMLLoader.load(getClass().getResource("../view/MovesHistory.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }

        Scene scene = StyleCss.getInstance().getScene(root);
        stage.setScene(scene);
        stage.show();
    }

}
