package sample.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michigan on 23.05.16.
 */
public class Sounds {
    private static Sounds instance = null;

    public String path = "target/classes/sample/sounds/9mm_gunshot.mp3";
    private List<Media> media = null;

    private AudioStream as;



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
        media = new ArrayList<>();
        media.add(new Media(new File(path).toURI().toString()));
        //path = name;
        //System.out.println(path);
        /*mediaPlayers = new ArrayList<>();
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayers.add(new MediaPlayer(media));

        InputStream is;
        try {
            is = new FileInputStream(path);
            as = new AudioStream(is);

        } catch(Exception e) {
            System.out.println("brak pliku: "+path);
        }
*/
    }

    public void playMove() {
        MediaPlayer mp = new MediaPlayer(media.get(0));
        mp.play();
    }
}

