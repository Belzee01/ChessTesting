package sample.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by krzysztof on 07.05.16.
 */
@AllArgsConstructor
public class CheckMessage implements Serializable {
    @Getter @Setter
    int checkedColor;
}
