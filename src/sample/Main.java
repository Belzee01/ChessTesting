package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.models.Sounds;
import sample.models.StyleCss;

import javax.swing.text.Style;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("view/newGame.fxml"));
        primaryStage.setTitle("Chess");
        Scene scene = StyleCss.getInstance().getScene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        Sounds.getInstance().setSounds("...");

    }


    public static void main(String[] args) {
        launch(args);
    }
}
