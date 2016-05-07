package sample.controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sample.GameEngine;
import sample.models.DrawAnswer;
import sample.models.DrawRequest;
import sample.models.Message;
import sample.models.ResignMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatWindowController {
    public ChatWindowController(){
        GameEngine.getInstance().setChatWindowController(this);
    }

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextArea textArea;

    @FXML
    private Button resignButton;

    @FXML
    private Button drawRequestButton;



    /**
     * Handler odpowiedzialny za przesyłąnie wiadomości i wyświetlanie jej na ekrany obu graczy
     * (przesyłanie nie jest jeszcze zaimplementowane)
     */
    public void sendAction(ActionEvent event){
        Text nick = new Text(GameEngine.getInstance().getNick());
        nick.setFill(Color.GREEN);
        Text text = new Text(": " + textArea.getText() + "\n");
        textFlow.getChildren().addAll(nick, text);
        textFlow.setStyle("-fx-background-color: white;");


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
        textFlow.setStyle("-fx-background-color: white;");
    }

    public void sendDrawRequest(ActionEvent event){
        DrawRequest drawRequest = new DrawRequest();
        GameEngine.getInstance().getTcpConnectionService().sendObject(drawRequest);
    }

    public void resign(ActionEvent event){
        ResignMessage message = new ResignMessage();
        GameEngine.getInstance().getTcpConnectionService().sendObject(message);
    }

    public void acceptDraw(ActionEvent event){
        DrawAnswer answer = new DrawAnswer(true);
        GameEngine.getInstance().getTcpConnectionService().sendObject(answer);
    }
    public void declineDraw(ActionEvent event){
        DrawAnswer answer = new DrawAnswer(false);
        GameEngine.getInstance().getTcpConnectionService().sendObject(answer);
    }



}
