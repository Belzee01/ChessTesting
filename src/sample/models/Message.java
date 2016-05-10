package sample.models;

import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Message implements Serializable{
    @Setter @Getter
    String textMessage;

    @Getter @Setter
    String nick;
}
