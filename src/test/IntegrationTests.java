package test;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import sample.Main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.loadui.testfx.GuiTest.find;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.api.FxToolkit.setupApplication;
import static org.testfx.api.FxToolkit.setupStage;

public class IntegrationTests extends ApplicationTest{

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {

    }

    @BeforeClass
    public static void setupSpec() throws Exception {
        primaryStage = registerPrimaryStage();
        setupStage(stage -> stage.show());
    }

    @Before
    public void setUp() throws Exception {
        setupApplication(Main.class);
    }

    @Test
    public void testInvalidPortNumber() throws InterruptedException {
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);

        Button button1 = find("#networkGameButton");
        assertNotEquals(button1, null);

        clickOn(button1);

        TextField textField = find("#listeningPortNumber");
        textField.setText("abc");

        assertNotEquals(find("#createGameButton"), null);
        clickOn("#createGameButton");

        Label label = find("#wrongPort");
        assertEquals(label.getText(), "Niewłaściwy nr portu");
    }

    @Test
    public void testEdgeValuesForPortNumberMin(){
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);

        Button button1 = find("#networkGameButton");
        assertNotEquals(button1, null);

        clickOn(button1);

        TextField textField = find("#listeningPortNumber");
        textField.setText("49153");

        assertNotEquals(find("#createGameButton"), null);
        clickOn("#createGameButton");

        Label label = find("#wrongPort");
        assertEquals(label.getText(), "Oczekiwanie na przeciwnika....");
    }

    @Test
    public void testEdgeValuesForPortNumberMax(){
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);

        Button button1 = find("#networkGameButton");
        assertNotEquals(button1, null);

        clickOn(button1);

        TextField textField = find("#listeningPortNumber");
        textField.setText("65534");

        assertNotEquals(find("#createGameButton"), null);
        clickOn("#createGameButton");

        Label label = find("#wrongPort");
        assertEquals(label.getText(), "Oczekiwanie na przeciwnika....");
    }

    @Test
    public void testNullValueInPort(){
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);

        Button button1 = find("#networkGameButton");
        assertNotEquals(button1, null);

        clickOn(button1);

        TextField textField = find("#listeningPortNumber");
        textField.setText(null);

        assertNotEquals(find("#createGameButton"), null);
        clickOn("#createGameButton");

        Label label = find("#wrongPort");
        assertEquals(label.getText(), "Niewałaściwy numer portu");
    }

    @Test
    public void testNullValueAsNick(){
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);

        Button button1 = find("#networkGameButton");
        assertNotEquals(button1, null);

        clickOn(button1);

        TextField textField1 = find("#listeningPortNumber");
        textField1.setText("50000");

        TextField textField = find("#serverNick");
        textField.setText(null);

        assertNotEquals(find("#createGameButton"), null);
        clickOn("#createGameButton");

        Label label = find("#wrongPort");
        assertEquals(label.getText(), "Musisz podać nick");
    }

    @Test
    public void testInvalidIPNumber() throws InterruptedException {
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
        textField2.setText("192.168.0.4.6");

        assertNotEquals(find("#joinGameButton"), null);
        clickOn(button2);

        Label label = find("#wrongData");
        assertEquals(label.getText(), "Niewłaściwy adres IP lu nr portu!!!");
    }

    @Test
    public void testEdgeValueIPMax() throws InterruptedException {
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
        textField2.setText("257.255.255.255");

        assertNotEquals(find("#joinGameButton"), null);
        clickOn(button2);

        Label label = find("#wrongData");
        assertEquals(label.getText(), "Niewłaściwy adres IP lu nr portu!!!");
    }

    @Test
    public void testEdgeValueIPMin() throws InterruptedException {
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
        textField2.setText("0.0.0.-1");

        assertNotEquals(find("#joinGameButton"), null);
        clickOn(button2);

        Label label = find("#wrongData");
        assertEquals(label.getText(), "Niewłaściwy adres IP lu nr portu!!!");
    }

    @Test
    public void testValidIPNumber() throws InterruptedException {
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
        textField2.setText("194.168.0.4");

        assertNotEquals(find("#joinGameButton"), null);
        clickOn(button2);

        Label label = find("#wrongData");
        assertEquals(label.getText(), "Oczekiwanie na przeciwnika....");
    }

    @Test
    public void testNullValueAsIP() throws InterruptedException {
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
        textField2.setText(null);

        assertNotEquals(find("#joinGameButton"), null);
        clickOn(button2);

        Label label = find("#wrongData");
        assertEquals(label.getText(), "Niewłaściwy adres IP lu nr portu!!!");
    }

    @Test
    public void testTCPCreateGame() throws InterruptedException {
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
        clickOn((Button)find("#createGameButton"));

        Thread.sleep(30000);
    }

    @Test
    public void testJoinGame() throws InterruptedException {
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);

        Button button1 = find("#networkGameButton");
        assertNotEquals(button1, null);

        clickOn(button1);

        clickOn("#Join");

        TextField textField = find("#hostPortNumber");
        textField.setText("50000");
        TextField textField1 = find("#clientNick");
        textField1.setText("b");
        TextField textField2 = find("#hostIP");
        textField2.setText("192.168.0.5");

        Button button2 = find("#joinGameButton");
        assertNotEquals(button2, null);
        clickOn(button2);

        Thread.sleep(20000);
    }

}
