package sample.controllers;


import javafx.application.Platform;
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
import sample.GameEngine;
import sample.services.GameCreatingService;
import sample.services.NetValidator;
import sample.services.TCPClientConnectionService;
import sample.services.TCPServerConnectionService;

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

        GameEngine.getInstance().setTcpConnectionService(new TCPServerConnectionService(Integer.valueOf(listeningPortNumber.getText())));

        GameEngine.getInstance().getTcpConnectionService().setOnConnectionEstablished(data -> {
            Platform.runLater(() ->{
                System.out.println("Established as server");
                showBoardOverview();
                Scene scene=new Scene(rootLayout);
                primaryStage.setScene(scene);
                primaryStage.show();
            });
        });

        GameEngine.getInstance().setNick(serverNick.getText());
        GameEngine.getInstance().getTcpConnectionService().startConnection();
        GameEngine.getInstance().setServerRole(true);

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

        GameEngine.getInstance().setTcpConnectionService(new TCPClientConnectionService(hostIP.getText(),Integer.valueOf(hostPortNumber.getText())));

        GameEngine.getInstance().getTcpConnectionService().setOnConnectionEstablished(data -> {
            Platform.runLater(() ->{
                System.out.println("Established as client");
                showBoardOverview();
                Scene scene=new Scene(rootLayout);
                primaryStage.setScene(scene);
                primaryStage.show();
            });
        });

        GameEngine.getInstance().setNick(clientNick.getText());
        GameEngine.getInstance().getTcpConnectionService().startConnection();
        GameEngine.getInstance().setServerRole(false);
    }


    /**
     * Metoda wyświetlająca szachownicę
     */
    private void showBoardOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            FXMLLoader chatLoader = new FXMLLoader();
            loader.setLocation(GameCreatingController.class.getResource("../view/BoardOverview.fxml"));
            chatLoader.setLocation(GameCreatingController.class.getResource("../view/ChatWindow.fxml"));
            rootLayout.setCenter(loader.load());
            rootLayout.setRight(chatLoader.load());

            // Give the controller access to the main app.

            BoardOverviewController controller = loader.getController();
            controller.initBoard();

            ChatWindowController chatController = chatLoader.getController();
            chatController.initChatWindow();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
