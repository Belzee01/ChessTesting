package sample.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
public class CheckMessage implements Serializable {
    @Getter @Setter
    int checkedColor;
}
