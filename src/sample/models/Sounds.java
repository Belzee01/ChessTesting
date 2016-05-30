package sample.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;;
import java.io.File;

/**
 * Klasa reprezentująca efekty dźwiękowe.
 */
public class Sounds {
    private static Sounds instance = null;
    private Media[] media = null;

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
        if (name == null) {
            media = null;
        } else {
            if (!name.equals("fun") && !name.equals("classic") && !name.equals("alternative"))
                return;
            String path = "target/classes/sample/sounds/" + name + "/";
            media = new Media[5];
            media[0] = new Media(new File(path+"alert.mp3").toURI().toString());
            media[1] = new Media(new File(path+"choose.mp3").toURI().toString());
            media[2] = new Media(new File(path+"move.mp3").toURI().toString());
            media[3] = new Media(new File(path+"check.mp3").toURI().toString());
            media[4] = new Media(new File(path+"mat.mp3").toURI().toString());
        }

    }

    /**
     * Dźwięk ruchu.
     * @param sound index danego dźwięku
     *              0 - alert
     *              1 - choose figure
     *              2 - move figure
     *              3 - check
     *              4 - mat / game_over
     * @return jeśli index jest poprawny true jeśli nie false
     */
    public boolean getSound(int sound) {
        if (media == null)
            return true;
        if (sound<0 || sound>=media.length)
            return false;
        MediaPlayer mp = new MediaPlayer(media[sound]);
        mp.play();
        return true;
    }
}

