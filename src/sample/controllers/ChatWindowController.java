package sample.controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sample.GameEngine;
import sample.models.Message;

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


    /**
     * Handler odpowiedzialny za przesyłąnie wiadomości i wyświetlanie jej na ekrany obu graczy
     * (przesyłanie nie jest jeszcze zaimplementowane)
     */
    public void sendAction(ActionEvent event){
        Text nick = new Text(GameEngine.getInstance().getNick());
        nick.setFill(Color.GREEN);
        Text text = new Text(": " + textArea.getText() + "\n");
        textFlow.getChildren().addAll(nick, text);


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

}
