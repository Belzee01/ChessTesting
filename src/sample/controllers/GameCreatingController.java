package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.services.GameCreatingService;
import sample.services.NetValidator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GameCreatingController implements Initializable{

    NetValidator validator;
    GameCreatingService service;

    private Stage primaryStage;
    private BorderPane rootLayout;

    @FXML
    private TextField listeningPortNumber;

    @FXML
    private TextField hostPortNumber;

    @FXML
    public TextField serverNick;

    @FXML
    public TextField clientNick;

    @FXML
    private TextField hostIP;

    @FXML
    private Label myIP;

    @FXML
    private Button createGameButton;

    @FXML
    private Button joinGameButton;

    @FXML
    private Button joiningBackButton;

    @FXML
    private Button creatingBackButton;

    @FXML
    private Label wrongData;

    @FXML
    private Label wrongPort;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validator = new NetValidator();
        service = new GameCreatingService();
        myIP.setText(service.getCurrentHostIpAddress());
    }

    public void backButtonAction(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        Parent root = new Parent() {};
        if(event.getSource() == joiningBackButton){
            stage = (Stage) joiningBackButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/gameType.fxml"));
        }
        else if(event.getSource() == creatingBackButton){
            stage = (Stage) creatingBackButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/gameType.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Handler odpowiedzialny za tworzenie gry na podstawie wprowadzonych parametrów
     */
    public void createGame(ActionEvent event){
        if(validator.checkPort(listeningPortNumber.getText())) {
        }
        else{
            wrongPort.setText("Niewłaściwy nr portu");
            return;
        }

        try{
            primaryStage = (Stage) createGameButton.getScene().getWindow();
            rootLayout = FXMLLoader.load(getClass().getResource("../view/RootLayout.fxml"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        showBoardOverview();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Handler odpowiedzialny za dołączanie do rozgrywki założonej przez drugiego gracza
     */
    public void joinGame(ActionEvent event){
        if(!(validator.checkIPAddress(hostIP.getText()) && validator.checkPort(hostPortNumber.getText()))){
            wrongData.setText("Niewłaściwy adres IP lu nr portu!!!");
            return;
        }

        try{
            primaryStage = (Stage) createGameButton.getScene().getWindow();
            rootLayout = FXMLLoader.load(getClass().getResource("../view/RootLayout.fxml"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        showBoardOverview();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


   /* private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameCreatingController.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();
            primaryStage.setScene(new Scene(rootLayout));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Metoda wyświetlająca szachownicę
     */
    private void showBoardOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameCreatingController.class.getResource("../view/BoardOverview.fxml"));
            rootLayout.setCenter(loader.load());

            // Give the controller access to the main app.

            BoardOverviewController controller = loader.getController();
            controller.initBoard("#fffdca", "#a58240");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
