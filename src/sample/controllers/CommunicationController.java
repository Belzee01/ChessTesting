package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lombok.Getter;
import sample.GameEngine;
import sample.models.*;

import java.io.IOException;

public class CommunicationController {
    public CommunicationController(){
        GameEngine.getInstance().setCommunicationController(this);
    }

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextArea textArea;

    @FXML @Getter
    private Button resignButton;

    @FXML
    private Button drawRequestButton;

    @FXML
    private Label TimeLabel;


    /**
     * Handler odpowiedzialny za przesyłąnie wiadomości i wyświetlanie jej na ekrany obu graczy
     * (przesyłanie nie jest jeszcze zaimplementowane)
     */
    public void sendAction(ActionEvent event){
        Text nick = new Text(GameEngine.getInstance().getNick());
        nick.setFill(Color.GREEN);
        Text text = new Text(": " + textArea.getText() + "\n");
        textFlow.getChildren().addAll(nick, text);
        //textFlow.setStyle("-fx-background-color: white;");


        Message msg=new Message();
        msg.setTextMessage(textArea.getText());
        msg.setNick(GameEngine.getInstance().getNick());

        textArea.clear();

        GameEngine.getInstance().getTcpConnectionService().sendObject(msg);
    }

    public void receive(Message msg){
        Text nick=new Text(msg.getNick());
        nick.setFill(Color.RED);

        Text message=new Text(": " +msg.getTextMessage()+"\n");
        textFlow.getChildren().addAll(nick,message);
    }

    public void initChatWindow(){
        //textFlow.setStyle("-fx-background-color: white;");
    }

    /**
     * Handler odpowiedzialny za wysłanie propozycji remisu.
     * @param event
     */
    public void sendDrawRequest(ActionEvent event){
        DrawRequest drawRequest = new DrawRequest();
        GameEngine.getInstance().getTcpConnectionService().sendObject(drawRequest);
    }

    public void resign(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("KONIEC GRY\nPrzegrałeś.");
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);

        alert.showAndWait();

    }


}
