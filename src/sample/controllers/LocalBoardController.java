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
import java.util.ArrayList;
import java.util.Optional;

@SuppressWarnings("Duplicates")
public class LocalBoardController {
    @FXML
    private GridPane gridPane;
    private GameEngine gameEngine = GameEngine.getInstance();
    private Images images = new Images();

    private String evenColor;
    private String oddColor;

    private static CounterService whiteCounter = new CounterService();
    private static CounterService blackCounter = new CounterService();

    public LocalBoardController() {
        GameEngine.getInstance().setChessLogicService(new ChessLogicService(new Board()));
        gameEngine.setServerRole(true);

        if (GameEngine.getInstance().getTimeGameMode() == -1) {
            whiteCounter.disableTimeOutMode();
            blackCounter.disableTimeOutMode();
        } else {
            whiteCounter.enableTimeOutMode(Duration.ofMinutes(
                    GameEngine.getInstance().getTimeGameMode()
            ));

            blackCounter.enableTimeOutMode(Duration.ofMinutes(
                    GameEngine.getInstance().getTimeGameMode()
            ));
        }

        whiteCounter.setOnTimeOut(data -> {
            Platform.runLater(() -> {
                onWhiteTimeOut();

            });
        });

        blackCounter.setOnTimeOut(data -> {
            Platform.runLater(() -> {
                onBlackTimeOut();
            });
        });

        blackCounter.setOnTick((data) -> {
            Platform.runLater(() -> {
                updateBlackLabel(data);
            });
        });

        whiteCounter.setOnTick((data) -> {
            Platform.runLater(() -> {
                updateWhiteLabel(data);
            });
        });

        whiteCounter.reset();
        blackCounter.reset();

        whiteCounter.stopTiming();
        blackCounter.stopTiming();

    }

    public void updateWhiteLabel(Duration dur) {
        if (GameEngine.getInstance().getTimeGameMode() != -1) {
            dur = Duration.ofMinutes(GameEngine.getInstance().getTimeGameMode()).minus(dur);
        }

        int minutes = (int) dur.getSeconds() / 60;
        int seconds = (int) dur.getSeconds() % 60;


        String secondsString = "";
        if (seconds < 10) {
            secondsString = "0";
        }
        secondsString += Integer.toString(seconds);

        String minutesString = "";
        if (minutes < 10) {
            minutesString = "0";
        }
        minutesString += Integer.toString(minutes);

        GameEngine.getInstance().getLocalTimeLabelsController().getWhiteTime().setText(minutesString + " : " + secondsString);
    }

    public void updateBlackLabel(Duration dur) {
        if (GameEngine.getInstance().getTimeGameMode() != -1) {
            dur = Duration.ofMinutes(GameEngine.getInstance().getTimeGameMode()).minus(dur);
        }

        int minutes = (int) dur.getSeconds() / 60;
        int seconds = (int) dur.getSeconds() % 60;


        String secondsString = "";
        if (seconds < 10) {
            secondsString = "0";
        }
        secondsString += Integer.toString(seconds);

        String minutesString = "";
        if (minutes < 10) {
            minutesString = "0";
        }
        minutesString += Integer.toString(minutes);

        GameEngine.getInstance().getLocalTimeLabelsController().getBlackTime().setText(minutesString + " : " + secondsString);
    }


    public void displayGameEndAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);

        ButtonType back = new ButtonType("Wróć do menu");
        ButtonType show = new ButtonType("Pokaż historię ruchów");

        alert.getButtonTypes().setAll(back, show);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == back) {
            backToMenu();
        } else if (result.get() == show) {
            showMovesHistory();
        }
    }

    public void onWhiteTimeOut() {
        whiteCounter.stopTiming();
        blackCounter.stopTiming();
        displayGameEndAlert("BIAŁYM skończył się czas");
    }

    public void onBlackTimeOut() {
        whiteCounter.stopTiming();
        blackCounter.stopTiming();
        displayGameEndAlert("Czarnym skończył się czas");
    }


    public void onBlackWin() {
        whiteCounter.stopTiming();
        blackCounter.stopTiming();
        displayGameEndAlert("CZARNI wygrali");
    }

    public void onWhiteWin() {
        whiteCounter.stopTiming();
        blackCounter.stopTiming();
        displayGameEndAlert("BIALI wygrali");
    }

    public void onBlackChecked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Czarne zostały zaszachowane");
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);
        alert.setWidth(300);
        alert.show();
    }

    public void onWhiteChecked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Białe zostały zaszachowane");
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

        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j) {

                Pane pane = new Pane();
                pane.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> refreshBoard());

                if (i % 2 == 0) {
                    if (j % 2 == 0) pane.setStyle(evenColor);
                    else pane.setStyle(oddColor);
                } else {
                    if (j % 2 == 0) pane.setStyle(oddColor);
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
        char[][] figuresPosition = gameEngine.getChessLogicService().getFiguresArray();

        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 8; ++j) {
                if (figuresPosition[j][i] == 0)
                    continue;
                ImageView iv = new ImageView(images.getFigureImage(figuresPosition[j][i]));
                iv.fitWidthProperty().bind(gridPane.widthProperty().divide(8));
                iv.fitHeightProperty().bind(gridPane.heightProperty().divide(8));

                if (GameEngine.getInstance().isServerRole()) {
                    if (figuresPosition[j][i] > 'a' && figuresPosition[j][i] < 'z') {
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> showMoves(iv));
                    } else {
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> refreshBoard());
                    }
                } else {
                    if (figuresPosition[j][i] > 'A' && figuresPosition[j][i] < 'Z') {
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> showMoves(iv));
                    } else {
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> refreshBoard());
                    }
                }

                gridPane.add(iv, i, j);
            }
    }

    /**
     * Handler wyświetlający możliwe ruchy dla wybranaj figury
     *
     * @param IV obiekt klasy ImageView dla którego sprawdzane są możliwe ruchy
     */
    private void showMoves(ImageView IV) {
        refreshBoard();

        gameEngine.setMoveX(GridPane.getColumnIndex(IV));
        gameEngine.setMoveY(GridPane.getRowIndex(IV));

        String[] availableMovesList = gameEngine.getChessLogicService().getPossibleMoves(
                GameEngine.getInstance().getChessLogicService().getBoard().getBoard(),
                GridPane.getColumnIndex(IV),
                GridPane.getRowIndex(IV)
        );

        ArrayList<String> listOfSaveMoves = new ArrayList<String>();

        for (int i = 0; i < availableMovesList.length; i++) {
            ChessLogicService localService = new ChessLogicService();
            localService.setBoard(new Board(availableMovesList[i], false));

            if (localService.getCheck() != (gameEngine.isServerRole() ? 0 : 1)) {
                listOfSaveMoves.add(availableMovesList[i]);
            }
        }
        boolean[][] possibleMoves = gameEngine.getChessLogicService().getPossibleMovesMask(listOfSaveMoves);


        possibleMoves[gameEngine.getMoveX()][gameEngine.getMoveY()] = false;

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
     *
     * @param iv - obiekt klasy ImageView; miejsce w które zostaje przesunięta figura dla której metoda zostaje wywołana
     */
    private void move(ImageView iv) {
        int code = gameEngine.localMove(GridPane.getColumnIndex(iv), GridPane.getRowIndex(iv));

        refreshBoard();
        switch (code) {
            case 1:
                onBlackChecked();
                break;
            case -1:
                onWhiteChecked();
                break;
            case -2:
                onBlackWin();
                break;
            case 2:
                onWhiteWin();
                break;
            default:
                break;
        }

        if (gameEngine.getChessLogicService().getBoard().getServerTurn()) {
            whiteCounter.startTiming();
            blackCounter.stopTiming();
        } else {
            whiteCounter.stopTiming();
            blackCounter.startTiming();
        }
    }

    public void backToMenu() {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        AnchorPane anchorPane = new AnchorPane();
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("../view/newGame.fxml"));
        } catch (IOException e) {
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
    public void showMovesHistory() {
        Stage stage = (Stage) gridPane.getScene().getWindow();
        Parent root = new Parent() {
        };
        try {
            root = FXMLLoader.load(getClass().getResource("../view/MovesHistory.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = StyleCss.getInstance().getScene(root);
        stage.setScene(scene);
        stage.show();
    }

}
