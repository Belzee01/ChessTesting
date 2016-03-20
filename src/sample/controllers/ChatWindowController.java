package sample.controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatWindowController {

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextArea textArea;





    public void sendAction(ActionEvent event){
        Text nick = new Text("krzysiek");
        nick.setFill(Color.GREEN);
        Text text = new Text(": " + textArea.getText() + "\n");
        textFlow.getChildren().addAll(nick, text);
        textArea.clear();

    }

}
