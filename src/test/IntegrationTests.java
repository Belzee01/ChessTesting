package test;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import sample.GameEngine;
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

        ComboBox comboBox = (ComboBox)find("#networkGameTimeBox");

        clickOn(comboBox);
        ObservableList<String> stringObservableList = comboBox.getItems();
        clickOn(stringObservableList.get(1));

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

    /**
     * Test if turn change works properly
     * @throws InterruptedException
     */
    @Test
    public void testLocalGameCreated() throws InterruptedException {
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);
        clickOn("#localGameButton");

        clickOn("#localGameTimeBox");
        ComboBox<String> comboBox = find("#localGameTimeBox");
        ObservableList<String> stringObservableList = comboBox.getItems();
        clickOn(stringObservableList.get(1));

        clickOn("#startLocalGame");

        GridPane gridPane = find("#gridPane");
        clickOn(gridPane.getChildren().get(57));

        clickOn(gridPane.getChildren().get(40));

        clickOn(gridPane.getChildren().get(40));

        clickOn(gridPane.getChildren().get(57));

        clickOn(gridPane.getChildren().get(9));

        clickOn(gridPane.getChildren().get(17));
    }

    /**
     * Test if next boards are properly recorded by history service
     * Standard check if for valid input we receive valid output
     */
    @Test
    public void testHistoryService() throws InterruptedException {
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
        textField2.setText("10.20.106.251");

        Button button2 = find("#joinGameButton");
        assertNotEquals(button2, null);
        clickOn(button2);

        Thread.sleep(3000);
        GridPane gridPane = find("#gridPane");
        clickOn(gridPane.getChildren().get(55));
        clickOn(gridPane.getChildren().get(47));

        clickOn(gridPane.getChildren().get(8));
        clickOn(gridPane.getChildren().get(16));
        clickOn(gridPane.getChildren().get(16));
        clickOn(gridPane.getChildren().get(24));

        String boardInitializer1 = "rnbqkbnr" + "pppppppp" + "        " + "        " + "        "
                + "P       " + " PPPPPPP" + "RNBQKBNR";

        String boardInitializer2 = "rnbqkbnr" + "ppppppp " + "       p" + "        " + "        "
                + "P       " + " PPPPPPP" + "RNBQKBNR";

        assertEquals(GameEngine.getInstance().getHistoryService().current().getBoard(), boardInitializer2);
        assertEquals(GameEngine.getInstance().getHistoryService().prev().getBoard(), boardInitializer1);

    }

    @Test
    public void testCheckAndMat(){
        Button button = find("#newGameButton");
        assertNotEquals(button, null);

        clickOn(button);
        clickOn("#localGameButton");

        clickOn("#localGameTimeBox");
        ComboBox<String> comboBox = find("#localGameTimeBox");
        ObservableList<String> stringObservableList = comboBox.getItems();
        clickOn(stringObservableList.get(1));

        clickOn("#startLocalGame");

        GridPane gridPane = find("#gridPane");
        ObservableList<Node> simulation = gridPane.getChildren();
        clickOn(gridPane.getChildren().get(52));
        clickOn(simulation.get(36));
        clickOn(simulation.get(8));
        clickOn(simulation.get(16));
        clickOn(simulation.get(61));
        clickOn(simulation.get(34));
        clickOn(simulation.get(16));
        clickOn(simulation.get(24));
        clickOn(simulation.get(59));
        clickOn(simulation.get(45));
        clickOn(simulation.get(24));
        clickOn(simulation.get(32));

        clickOn(simulation.get(45));
        clickOn(simulation.get(13));
    }

}
