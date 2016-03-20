package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardOverviewController {
    @FXML
    private GridPane gridPane;

    private String evenColor;
    private String oddColor;

    public BoardOverviewController() {
    }

    public void initBoard(String evenColor, String oddColor) {
        GameController.setFigures();
        GameController.setMoves();
        final String CSS = "-fx-background-color: ";
        final String SEMICOLON = ";";
        this.evenColor = CSS + evenColor + SEMICOLON;
        this.oddColor = CSS + oddColor + SEMICOLON;
        refreshBoard();
    }

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


    private void drawFigures() {
        char [][] figuresPosition = GameController.getFiguresPosition();

        for(int i=0; i<8; ++i)
            for(int j=0; j<8; ++j) {
                if (figuresPosition[i][j]==0)
                    continue;
                ImageView iv = new ImageView(Images.getFigureImage(figuresPosition[i][j]));
                iv.fitWidthProperty().bind(gridPane.widthProperty().divide(8));
                iv.fitHeightProperty().bind(gridPane.heightProperty().divide(8));

                if(GameController.COLOR.compareTo("WHITE") == 0) {
                    if (figuresPosition[i][j] > 'a' && figuresPosition[i][j] < 'z')
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> showMoves(iv));
                    else
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> refreshBoard());
                } else {
                    if (figuresPosition[i][j] > 'A' && figuresPosition[i][j] < 'Z')
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> showMoves(iv));
                    else
                        iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> refreshBoard());
                }

                gridPane.add(iv, j, i);
            }
    }


    private void showMoves(ImageView IV) {
        refreshBoard();
        GameController.setMove(GridPane.getRowIndex(IV), GridPane.getColumnIndex(IV));
        boolean [][] possibleMoves = GameController.getPossibleMoves();
        for(int i=0; i<8; ++i)
            for(int j=0; j<8; ++j)
                if(possibleMoves[i][j]) {
                    ImageView iv = new ImageView(Images.getMoveImage(j, i));
                    iv.fitWidthProperty().bind(gridPane.widthProperty().divide(8));
                    iv.fitHeightProperty().bind(gridPane.heightProperty().divide(8));
                    iv.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> move(iv));

                     /* add image */
                    gridPane.add(iv, i, j);
                }
    }

    private void move(ImageView iv) {
        GameController.move(GridPane.getRowIndex(iv), GridPane.getColumnIndex(iv));
        refreshBoard();
    }


    public void openChatWindow(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        try{
            root = FXMLLoader.load(getClass().getResource("../view/ChatWindow.fxml"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        root.setStyle("-fx-background-color: #C4C4C4;");
        Scene scene = new Scene(root, 300, 300);
        stage.setTitle("Czat");
        stage.setScene(scene);
        stage.show();

    }

    /*
    public void paintBoard(String even, String odd) {
        int column = 0;
        boolean row = true;
        final String css = "-fx-background-color: ";

        for(Node node: board.getChildren()) {
            if(column==8) {
                column = 0;
                row = !row;
            }
            if(row) {
                if(column%2==0) node.setStyle(css + even + ";");
                else node.setStyle(css + odd + ";");
            } else {
                if(column%2==0) node.setStyle(css + odd + ";");
                else node.setStyle(css + even + ";");
            }
            ++column;
        }
    }
*/
}
