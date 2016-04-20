package sample.models;

import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by krzysztof on 20.04.16.
 */
public class Message implements Serializable{
    @Setter @Getter
    String textMessage;

    @Getter @Setter
    String nick;
}
