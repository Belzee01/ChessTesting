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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.GameEngine;
import sample.models.StyleCss;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LocalGameController implements Initializable {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private String whiteNick;
    private String blackNick;

    @FXML
    private TextField firstNick;

    @FXML
    private TextField secondNick;

    @FXML
    private Button backToGameType;

    @FXML
    private ComboBox<String> localGameTimeBox;

    ObservableList<String> timeList = FXCollections.observableArrayList("Bez limitu", "2","10", "15", "20", "30", "40");

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
        Scene scene = StyleCss.getInstance().getScene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handler służący do wyświetlenie okna gry lokalnej (na razie niezaimplementowane)
     */
    public void startLocalGame(ActionEvent event){
        try{
            primaryStage = new Stage();
            rootLayout = FXMLLoader.load(getClass().getResource("../view/RootLayout.fxml"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        int mode=-1;

        switch (localGameTimeBox.getValue()){
            case "2":
                mode=2;
                break;
            case "10":
                mode=10;
                break;
            case "15":
                mode=15;
                break;
            case "20":
                mode=20;
                break;
            case "30":
                mode=30;
                break;
            case "40":
                mode=40;
                break;
            default:
                mode=-1;
        }

        GameEngine.getInstance().setTimeGameMode(mode);
        Stage oldStage = (Stage) backToGameType.getScene().getWindow();
        oldStage.close();
        showBoardOverview();
        Scene scene= StyleCss.getInstance().getScene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Metoda wyświetlająca szachownicę
     */
    private void showBoardOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LocalGameController.class.getResource("../view/LocalBoardOverview.fxml"));
            FXMLLoader labels = new FXMLLoader();
            labels.setLocation(LocalGameController.class.getResource("../view/TimeLabels.fxml"));

            rootLayout.setCenter(loader.load());
            rootLayout.setRight(labels.load());

            GameEngine.getInstance().setLocalTimeLabelsController(labels.getController());

            // Give the controller access to the main app.

            LocalBoardController controller = loader.getController();
            controller.initBoard();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
