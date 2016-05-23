package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import sample.GameEngine;
import sample.models.DrawAnswer;
import sample.models.DrawRequest;
import sample.models.Message;
import sample.models.ResignationMessage;

public class CommunicationController {
    public CommunicationController(){
        GameEngine.getInstance().setCommunicationController(this);
    }

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextArea textArea;

    @FXML
    private Button resignButton;

    @FXML
    private Button drawRequestButton;

    @FXML
    private Button makeDrawButton;

    @FXML
    private Button cancelDrawButton;

    @FXML
    private Button acceptDrawButton;

    @FXML
    private Button opponentResignedButton;

    @FXML
    private Button ErrorConfirmButton;



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

    public void sendDrawRequest(ActionEvent event){
        DrawRequest drawRequest = new DrawRequest();
        GameEngine.getInstance().getTcpConnectionService().sendObject(drawRequest);
    }

    public void resign(ActionEvent event){
        ResignationMessage message = new ResignationMessage();
        GameEngine.getInstance().getTcpConnectionService().sendObject(message);
    }

    public void acceptDraw(ActionEvent event){
        DrawAnswer answer = new DrawAnswer(true);
        GameEngine.getInstance().getTcpConnectionService().sendObject(answer);
        Stage stage = (Stage) acceptDrawButton.getScene().getWindow();
        stage.close();
    }
    public void declineDraw(ActionEvent event){
        DrawAnswer answer = new DrawAnswer(false);
        GameEngine.getInstance().getTcpConnectionService().sendObject(answer);
        Stage stage = (Stage) acceptDrawButton.getScene().getWindow();
        stage.close();
    }
    public void makeDraw(ActionEvent event){
        /* KONIEC GRY - REMIS */
        Stage stage = (Stage) makeDrawButton.getScene().getWindow();
        stage.close();
    }
    public void cancelDraw(ActionEvent event){
        Stage stage = (Stage) cancelDrawButton.getScene().getWindow();
        stage.close();
    }
    public void opponentResignation(ActionEvent event){
        /* KONIEC GRY - PRZECIWNIK ZREZYGNOWAŁ */
        Stage stage = (Stage) opponentResignedButton.getScene().getWindow();
        stage.close();
    }

    public void connectionErrorConfirm(ActionEvent event){
        Stage stage = (Stage) ErrorConfirmButton.getScene().getWindow();
        stage.close();
    }

}
