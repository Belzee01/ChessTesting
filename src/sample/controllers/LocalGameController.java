package sample.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LocalGameController implements Initializable {

    @FXML
    private TextField firstNick;

    @FXML
    private TextField secondNick;

    @FXML
    private Button backToGameType;

    @FXML
    private ComboBox<String> localGameTimeBox;

    ObservableList<String> timeList = FXCollections.observableArrayList("Bez limitu", "10", "15", "20", "30", "40");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localGameTimeBox.setItems(timeList);
    }

    public void handeButtonAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = new Parent() {};
        if(event.getSource() == backToGameType){
            stage = (Stage) backToGameType.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/gameType.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Handler służący do wyświetlenie okna gry lokalnej (na razie niezaimplementowane)
     */
    public void startLocalGame(ActionEvent event){
    }

}
