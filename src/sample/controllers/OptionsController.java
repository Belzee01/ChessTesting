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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.models.Sounds;
import sample.models.StyleCss;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements Initializable{

    @FXML
    private ComboBox<String> stylesBox;

    @FXML
    private ComboBox<String> soundsBox;

    @FXML
    private Button applyOptionsButton;

    private ObservableList<String> stylesList;
    private ObservableList<String> soundsList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stylesList = FXCollections.observableArrayList("classic", "dark", "blue", "green", "yellow");
        stylesBox.setItems(stylesList);

        soundsList = FXCollections.observableArrayList("Bez dźwięku", "classic", "funny", "alternative");
        soundsBox.setItems(soundsList);
    }

    /**
     * Handler ustawiający wybrane opcje, takie jak dźwięki i skórki.
     * @param event
     */
    public void applyOptions(ActionEvent event){
        String styleName = stylesBox.getValue();
        System.out.println(styleName);
        StyleCss.getInstance().setStyle(styleName);

        String sound = soundsBox.getValue();
        Sounds.getInstance().setSounds(sound);

        Stage stage;
        AnchorPane anchorPane = new AnchorPane();
        try{
            stage = (Stage) applyOptionsButton.getScene().getWindow();
            anchorPane = FXMLLoader.load(getClass().getResource("../view/newGame.fxml"));
        }catch (IOException e) {
            e.printStackTrace();
            stage = new Stage();
        }
        Scene scene = StyleCss.getInstance().getScene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
}
