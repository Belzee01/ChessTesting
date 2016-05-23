package sample.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michigan on 23.05.16.
 */
public class Sounds {
    private static Sounds instance = null;

    public String path = "target/classes/sample/sounds/9mm_gunshot.mp3";
    private List<MediaPlayer> mediaPlayers;

    private Sounds() {

    }

    /**
     * Klasyczny singleton
     *
     * @return instancja obiektu
     */
    public static Sounds getInstance() {
        if (instance == null)
            instance = new Sounds();
        return instance;
    }

    public void setSounds(String name) {
        //System.out.println(path);
        mediaPlayers = new ArrayList<>();
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayers.add(new MediaPlayer(media));
    }

    public void playMove() {
        if(mediaPlayers!=null) {
            mediaPlayers.get(0).stop();
            mediaPlayers.get(0).play();
        }
    }
}

