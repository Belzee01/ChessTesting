package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button newGameButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button localGameButton;

    @FXML
    private Button networkGameButton;

    @FXML
    private Button backToNewGame;

    @FXML
    private Button hostGameButton;

    @FXML
    private Button joinGameButton;

    @FXML
    private Button backToGameType;


    public void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = new Parent() {};
        if(event.getSource() == newGameButton){
            stage = (Stage) newGameButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/gameType.fxml"));
        }
        else if(event.getSource() == exitButton){
            System.exit(0);
        }
        else if(event.getSource() == backToNewGame){
            stage = (Stage) backToNewGame.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/newGame.fxml"));
        }
        else if(event.getSource() == localGameButton){
            stage = (Stage) localGameButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/localGameCreating.fxml"));
        }
        else if(event.getSource() == networkGameButton){
            stage = (Stage) networkGameButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/networkGameCreating.fxml"));
        }
        else if(event.getSource() == backToGameType){
            stage = (Stage) backToGameType.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/gameType.fxml"));
        }

        else{}

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();




    }
}
