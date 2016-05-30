package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import sample.models.StyleCss;

import java.io.IOException;


public class LocalTimeLabelsController {
    @FXML @Getter
    private Label whiteTime;

    @FXML @Getter
    private Label blackTime;


    public void onExitGameClicked(){
        Stage stage = (Stage) whiteTime.getScene().getWindow();
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
}
