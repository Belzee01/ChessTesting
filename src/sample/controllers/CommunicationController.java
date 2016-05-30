package sample.controllers;


import javafx.application.Platform;
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
import sample.services.CounterService;

import java.io.IOException;
import java.time.Duration;
import java.util.Timer;

public class CommunicationController {
    public CommunicationController(){
        GameEngine.getInstance().setCommunicationController(this);
        GameEngine.getInstance().getCounterService().setOnTick(
                data -> {
                    Platform.runLater(() ->{
                        updateTimeLabel(data);
                    });
        });
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

    public void updateTimeLabel(Duration dur){
        if(GameEngine.getInstance().getTimeGameMode()!=-1){
            dur=Duration.ofMinutes(GameEngine.getInstance().getTimeGameMode()).minus(dur);
        }

        int minutes=(int)dur.getSeconds()/60;
        int seconds=(int)dur.getSeconds()%60;


        String secondsString="";
        if (seconds<10){
            secondsString="0";
        }
        secondsString+=new Integer(seconds).toString();

        String minutesString="";
        if (minutes<10){
            minutesString="0";
        }
        minutesString+=new Integer(minutes).toString();

        TimeLabel.setText(minutesString+" : " +secondsString);
    }
    /**
     * Handler odpowiedzialny za przesyłąnie wiadomości i wyświetlanie jej na ekrany obu graczy
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
        GameEngine.getInstance().getTcpConnectionService().sendObject(new ResignationMessage());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("KONIEC GRY\nPrzegrałeś.");
        alert.setHeaderText(null);
        alert.setTitle(null);
        alert.setGraphic(null);
        alert.showAndWait();

        Stage stage = (Stage) this.getResignButton().getScene().getWindow();
        AnchorPane anchorPane = new AnchorPane();
        try{
            anchorPane = FXMLLoader.load(getClass().getResource("../view/newGame.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }

        Scene scene = StyleCss.getInstance().getScene(anchorPane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
