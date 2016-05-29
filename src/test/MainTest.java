package test;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import sample.Main;

import static org.junit.Assert.assertNotEquals;
import static org.loadui.testfx.GuiTest.find;
import static org.testfx.api.FxToolkit.*;

public class MainTest extends ApplicationTest{
    private static Stage primaryStage;

    private String ip;

    @BeforeClass
    public static void setupSpec() throws Exception {
        primaryStage = registerPrimaryStage();
        setupStage(stage -> stage.show());
    }

    @Before
    public void setUp() throws Exception {
        setupApplication(Main.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Test
    public void testIfClicked() throws InterruptedException {
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);

        Button button1 = find("#networkGameButton");
        assertNotEquals(button1, null);

        clickOn(button1);

        TextField textField = find("#listeningPortNumber");
        textField.setText("50000");
        TextField textField1 = find("#serverNick");
        textField1.setText("a");

        assertNotEquals(find("#createGameButton"), null);
        clickOn("#createGameButton");

        Label label = find("#wrongPort");
        assertNotEquals(label.getText(), "Niewłaściwy nr portu");

        Thread.sleep(40000);
        GridPane gridPane = find("#gridPane");
        assertNotEquals(gridPane, null);

        clickOn(gridPane.getHgap(), 0);
    }

    @Test
    public void testSecondInstance() throws InterruptedException {
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);

        Button button1 = find("#networkGameButton");
        assertNotEquals(button1, null);

        clickOn(button1);

        clickOn("#Join");

        Button button2 = find("#joinGameButton");
        assertNotEquals(button2, null);

        TextField textField = find("#hostPortNumber");
        textField.setText("50000");
        TextField textField1 = find("#clientNick");
        textField1.setText("b");
        TextField textField2 = find("#hostIP");
        textField2.setText("192.168.0.4");

        assertNotEquals(find("#joinGameButton"), null);
        clickOn(button2);

        Label label = find("#wrongPort");
        //assertNotEquals(label.getText(), "Niewłaściwy nr portu");

        Thread.sleep(20000);
    }
}