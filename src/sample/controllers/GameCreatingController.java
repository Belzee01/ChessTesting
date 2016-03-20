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

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;


public class GameCreatingController implements Initializable{

    //ChessService service;

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
        myIP.setText(getCurrentEnvironmentNetworkIp());
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

    public void createGame(ActionEvent event) throws Exception{
        if(checkPort(listeningPortNumber.getText())) {
            //connectAsServer(Integer.valueOf(listeningPortNumber.getText()));
            System.out.println("Port nasłuhujący: " + listeningPortNumber.getText());
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

    public void joinGame(ActionEvent event){
        if(checkIPAddress(hostIP.getText()) && checkPort(hostPortNumber.getText())){
            //connectAsClient(Integer.valueOf(hostIP.getText(), listeningPortNumber.getText()));
            System.out.println("IP hosta: " + hostIP.getText()+"    Port hosta: "+hostPortNumber.getText());
        }
        else{
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

    public boolean checkIPAddress(String ip){
        String[] checkers = ip.split("\\.");
        if(checkers.length != 4){
            return false;
        }
        for(int i=0; i<checkers.length; i++){
                for (int j=0; j<checkers[i].length(); j++){
                    if(!Character.isDigit(checkers[i].charAt(j)))
                        return false;
                }
        }
        return true;
    }
    
    public boolean checkPort(String port){
        if(port.length()==0)
            return false;
        for(int i=0; i<port.length(); i++){
            if(!Character.isDigit(port.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static String currentHostIpAddress;

  /*
  * Metoda zwracająca adres ip komputera
  */
  public static String getCurrentEnvironmentNetworkIp() {
        if (currentHostIpAddress == null) {
            Enumeration<NetworkInterface> netInterfaces = null;
            try {
                netInterfaces = NetworkInterface.getNetworkInterfaces();

                while (netInterfaces.hasMoreElements()) {
                    NetworkInterface ni = netInterfaces.nextElement();
                    Enumeration<InetAddress> address = ni.getInetAddresses();
                    while (address.hasMoreElements()) {
                        InetAddress addr = address.nextElement();
                        if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()
                                && !(addr.getHostAddress().indexOf(":") > -1)) {
                            currentHostIpAddress = addr.getHostAddress();
                        }
                    }
                }
                if (currentHostIpAddress == null) {
                    currentHostIpAddress = "127.0.0.1";
                }

            } catch (SocketException e) {
                currentHostIpAddress = "127.0.0.1";
            }
        }
        return currentHostIpAddress;
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
