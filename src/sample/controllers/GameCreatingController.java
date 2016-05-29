package sample.controllers;


import javafx.application.Platform;
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
    private Button joiningBackButton;

    @FXML
    private Label wrongData;

    @FXML
    private Label wrongPort;


    @FXML
    private ComboBox<String> networkGameTimeBox;

    ObservableList<String> timeList = FXCollections.observableArrayList("Bez limitu", "10", "15", "20", "30", "40");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validator = new NetValidator();
        service = new GameCreatingService();
        myIP.setText(service.getCurrentHostIpAddress());
        networkGameTimeBox.setItems(timeList);
    }

    public void backButtonAction(ActionEvent event) throws IOException{
        Stage stage = (Stage) joiningBackButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../view/gameType.fxml"));

        Scene scene = StyleCss.getInstance().getScene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Handler odpowiedzialny za tworzenie gry na podstawie wprowadzonych parametrów
     */
    public void createGame(ActionEvent event){
        if(validator.checkPort(listeningPortNumber.getText())) {
            wrongPort.setText("Oczekiwanie na przeciwnika....");
        }
        else{
            wrongPort.setText("Niewłaściwy nr portu");
            return;
        }

        String time = networkGameTimeBox.getValue();
        System.out.println(time);

        try{
            primaryStage = new Stage();
            rootLayout = FXMLLoader.load(getClass().getResource("../view/RootLayout.fxml"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        GameEngine.getInstance().setTcpConnectionService(new TCPServerConnectionService(Integer.valueOf(listeningPortNumber.getText())));

        GameEngine.getInstance().getTcpConnectionService().setOnConnectionEstablished(data -> {
            Platform.runLater(() ->{
                System.out.println("Established as server");
                Stage oldStage = (Stage) listeningPortNumber.getScene().getWindow();
                oldStage.close();
                showBoardOverview();
                Scene scene= StyleCss.getInstance().getScene(rootLayout);
                primaryStage.setScene(scene);
                primaryStage.show();
            });
        });

        int mode=-1;

        switch (networkGameTimeBox.getValue()){
            case "10":
                mode=1;
                break;
            case "15":
                mode=10;
                break;
            case "20":
                mode=10;
                break;
            case "30":
                mode=10;
                break;
            case "40":
                mode=10;
                break;
            default:
                mode=-1;
        }
        GameEngine.getInstance().setTimeGameMode(mode);

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
        } else{
            wrongData.setText("Oczekiwanie na przeciwnika....");
        }

        try{
            primaryStage = new Stage();
            rootLayout = FXMLLoader.load(getClass().getResource("../view/RootLayout.fxml"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        GameEngine.getInstance().setTcpConnectionService(new TCPClientConnectionService(hostIP.getText(),Integer.valueOf(hostPortNumber.getText())));

        GameEngine.getInstance().getTcpConnectionService().setOnConnectionEstablished(data -> {
            Platform.runLater(() ->{
                System.out.println("Established as client");
                Stage oldStage = (Stage) hostPortNumber.getScene().getWindow();
                oldStage.close();
                showBoardOverview();
                Scene scene=StyleCss.getInstance().getScene(rootLayout);
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

            CommunicationController chatController = chatLoader.getController();
            chatController.initChatWindow();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
