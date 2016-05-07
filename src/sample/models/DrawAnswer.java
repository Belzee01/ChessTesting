package sample.models;

import java.io.Serializable;

public class DrawAnswer implements Serializable{
    private boolean accepted;


    public DrawAnswer(boolean flag) {
        this.accepted = flag;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
