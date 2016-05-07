package sample.models;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Singlethon pozwalający ustawiać skórki i style do tworzonych scen.
 */
public class StyleCss {

    private static StyleCss style = null;
    private String path = null;
    private StyleCss() {};

    /**
     * Klasyczny singleton
     * @return instancja obiektu
     */
    public static StyleCss getInstance(){
        if(style == null)
            style = new StyleCss();

        return style;
    }

    public void setStyle(String path) {
        //Klasyczna skórka
        if(path == null)
            this.path = StyleCss.class.getResource("../view/styles/dark.css").toString();
        else
            this.path = StyleCss.class.getResource("../view/styles/"+path+".css").toString();
    }

    /**
     * Do towrzenia nowych scen używamy tylko tej metody!
     * @param root - plik fxml wczytany przez FXMLoader
     * @return
     */
    public Scene getScene(Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(path);
        return scene;
    }
    /**
     * Do towrzenia nowych scen używamy tylko tej metody!
     * @param root plik fxml wczytany przez FXMLoader
     * @param width szerokosć nowej sceny
     * @param height wysokość nowej sceny
     * @return nowy obiekt sceny
     */
    public Scene getScene(Parent root, int width, int height) {
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(path);
        return scene;
    }
}
