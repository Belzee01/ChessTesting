package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.GameEngine;
import sample.models.Board;
import sample.models.StyleCss;

import java.io.IOException;

/**
 * Kontroler do okna wyświetlającego historię ruchów
 */
public class MovesHistoryController {

    private BoardOverviewController boardOverviewController;


    @FXML
    private Button backToMenu;

    @FXML
    private Button nextBoard;

    @FXML
    private Button prevBoard;


    public void getNextBoard(ActionEvent event){
        Board board = GameEngine.getInstance().getHistoryService().next();
        if(board!=null) {
            /**zmiana widoku*/
        }
    }

    public void getPrevBoard(ActionEvent event){
        Board board = GameEngine.getInstance().getHistoryService().prev();
        if(board!=null) {
            /**zmiana widoku*/
        }
    }

    public void backToMenu(ActionEvent event){
        Stage oldStage = (Stage) backToMenu.getScene().getWindow();
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/newGame.fxml"));
        } catch(IOException e){

            root = new Parent() {};
            e.printStackTrace();
        }
        Scene scene = StyleCss.getInstance().getScene(root);
        oldStage.close();
        stage.setScene(scene);
        stage.show();


    }


}
