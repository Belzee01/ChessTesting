package sample.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca efekty dźwiękowe.
 */
public class Sounds {
    private static Sounds instance = null;
    private String path;
    private List<Media> media = null;

    private Sounds() {
        path = "target/classes/sample/sounds/9mm_gunshot.mp3";
    }

    /**
     * Singleton
     * @return instancja obiektu
     */
    public static Sounds getInstance() {
        if (instance == null)
            instance = new Sounds();
        return instance;
    }

    /**
     * Ustawia motyw dźwiękowy.
     * @param name - nazwa motywu dźwiękowego
     */
    public void setSounds(String name) {
        media = new ArrayList<>();
        media.add(new Media(new File(path).toURI().toString()));
    }

    /**
     * Dźwięk ruchu przeciwnika.
     */
    public void opponentMove() {
        MediaPlayer mp = new MediaPlayer(media.get(0));
        mp.play();
    }
}

