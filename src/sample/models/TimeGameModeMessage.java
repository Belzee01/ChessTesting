package sample.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by krzysztof on 29.05.16.
 */
@AllArgsConstructor
public class TimeGameModeMessage implements Serializable {
    // jeżeli tryb gry ograniczenia czasu to -1 w przeciwnym wypadku ilość minut na gracza
    @Getter
    @Setter
    private int timeGameMode;
}
