package test;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import sample.controllers.MenuController;
import sample.models.StyleCss;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.loadui.testfx.Assertions.verifyThat;

public class MainTest extends GuiTest {

    private Parent parent = null;

    @Override
    protected Parent getRootNode() {

        try {
            parent = FXMLLoader.load(getClass().getResource("../sample/view/newGame.fxml"));
            return parent;
        } catch (IOException ex) {
            // TODO ...
        }
        return parent;
    }

    @Test
    public void testIfCreated() throws InterruptedException, IOException {

        Button button = (Button) parent.lookup("#newGameButton");

        assertEquals(button.getText(), "Nowa gra");
        click(button);
    }
}