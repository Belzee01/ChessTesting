package sample.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Board {
    @Getter @Setter
    private String board;

    public Board(){
        board = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "        " + "PPPPPPPP" + "RNBQKBNR";
    }
}
