package test;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import sample.GameEngine;
import sample.Main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.loadui.testfx.GuiTest.find;
import static org.testfx.api.FxToolkit.*;

public class MainTests extends ApplicationTest{
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

    /**
     * Usage of chat according to "Rozmowa tekstowa pomiędzy graczami wtrakcie gry (CHAT)."
     * Tree scenarios of usage
     */

    /**
     * First scenario (Main)
     * 1. W trakcie gry dowolny użytkownik ma do dyspozycji okno
     *    czatu, pole do wpisywania wiadomości oraz przycisk„Wyślij”,
     * 2. Użytkownik klika na pole do wpisywania wiadomości,
     * 3. Użytkownik wpisuje wiadomość,
     * 4. Użytkownik naciska przycisk „Wyślij”.
     */
    /**
     * Test passed
     * @throws InterruptedException
     */
    @Test
    public void testFirstScenarioChat() throws InterruptedException {

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
        textField2.setText("192.168.0.4");

        Button button2 = find("#joinGameButton");
        assertNotEquals(button2, null);
        clickOn(button2);

        TextArea textArea = find("#textArea");

        assertNotEquals(textArea, null);

        clickOn(textArea);
        textArea.setText("Test chat #1");
        clickOn("#sendButton");

        Thread.sleep(10000);
    }

    /**
     * Scenariusz poboczny 1, punkt 4: Użytkownik wpisał za dużą
     * ilość znaków ( więcej niż 200 znaków).
     • wiadomość nie zostaje przesłana,
     • pojawia się komunikat, że wpisana wiadomość jest za długa,
     • powrót do poprzedniego stanu gry.
     */
    /**
     * Test failed
     * Manual test with 2000 char crashed application
     * @throws InterruptedException
     */
    @Test
    public void testSecondScenarioChat() throws InterruptedException {

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
        textField2.setText("127.0.0.1");

        Button button2 = find("#joinGameButton");
        assertNotEquals(button2, null);
        clickOn(button2);

        Thread.sleep(3000);
        TextArea textArea = find("#textArea");

        assertNotEquals(textArea, null);

        clickOn(textArea);
        String str = "lllllaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                +" aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        Thread.sleep(1500);
        System.out.println(str.length());
        textArea.setText(str);
        clickOn("#sendButton");

        Thread.sleep(10000);
    }

    /**
     * Scenariusz poboczny 2, punkt 4: Użytkownik nie wpisał
     * żadnego znaku.
     * • pomimo kliknięcia „Wyślij” użytkownik nie wysyła żadnej wiadomości,
     * • powrót do poprzedniego stanu gry.
     */
    /**
     * Test fails
     * Application crashes if given String to be send is null
     * @throws InterruptedException
     */
    @Test
    public void testThirdScenarioChat() throws InterruptedException {

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
        textField2.setText("192.168.43.135");

        Button button2 = find("#joinGameButton");
        assertNotEquals(button2, null);
        clickOn(button2);

        TextArea textArea = find("#textArea");

        assertNotEquals(textArea, null);


        clickOn(textArea);

        String str = "";

        System.out.println(str.length());
        textArea.setText(str);
        clickOn("#sendButton");

        Thread.sleep(10000);
    }

    /**
     * "Wybór „skórki” GUI (graficznego interfejsu użytkownika) (WSG)."
     * One scenario
     */
    /**
     * Scenariusz główny:
     * 1. W oknie głównym aplikacji użytkownik wyświetla między
     * innymi przycisk „Opcje”,
     * 2. Użytkownik klika przycisk „Opcje”,
     * 3. Użytkownik wyświetla między innymi pole tekstowe
     * „Skórka GUI”, oraz towarzyszące mu rozsuwane pole wyboru,
     * 4. Użytkownik klika dany element pola wyboru skórki GUI,
     * 5. Zmiana skórki GUI.
     */
    @Test
    public void testScenarioGUILook() throws InterruptedException {
        Button button = find("#optionsButton");
        clickOn(button);

        ComboBox comboBox = find("#stylesBox");
        clickOn(comboBox).clickOn("dark").clickOn("#applyOptionsButton");

        clickOn("#optionsButton");

        clickOn("#stylesBox").clickOn("green").clickOn("#applyOptionsButton");

        clickOn("#optionsButton");

        clickOn("#stylesBox").clickOn("blue").clickOn("#applyOptionsButton");
    }

    /**
     * Nazwa przypadku użycia: Kolorowanie możliwych ruchów figury (KRF).
     */
    @Test
    public void testColoringFields() throws InterruptedException {
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
        clickOn(gridPane.getChildren().get(49));

        Thread.sleep(2000);
    }

    /**
     * Scenariusz alternatywny 1, punkt 1: Użytkownik klika na nie swoją figurę:
     • Nie podświetla się figura kliknięta,
     • Nie podświetlają się żadne możliwe ruchy danej figury.
     */
    @Test
    public void testColoringFieldsSA1() throws InterruptedException {
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
        clickOn(gridPane.getChildren().get(11));

        Thread.sleep(2000);
    }

    /**
     * Scenariusz alternatywny 2, punkt 1: Użytkownik klika na swoją
     * figurę, kiedy nie jest jego kolej na wykonanie ruchu:
     • Nie podświetla się figura kliknięta,
     • Nie podświetlają się żadne możliwe ruchy danej figury.
     */
    @Test
    public void testColoringFieldsSA2() throws InterruptedException {
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
        clickOn(gridPane.getChildren().get(55));
        clickOn(gridPane.getChildren().get(47));

        clickOn(gridPane.getChildren().get(8));
        clickOn(gridPane.getChildren().get(16));
        clickOn(gridPane.getChildren().get(16));
        clickOn(gridPane.getChildren().get(24));

        String board = GameEngine.getInstance().getChessLogicService().getBoard().getBoard();
        String boardInitializer2 = "rnbqkbnr" + "ppppppp " + "       p" + "        " + "        "
                + "P       " + " PPPPPPP" + "RNBQKBNR";
        assertEquals(board, boardInitializer2);

        Thread.sleep(2000);
    }

    /**
     * Nazwa przypadku użycia: Wybór pomiędzy grą bez limitu
     * czasowego, a grą z limitem czasowym (L/NL).
     * @throws InterruptedException
     */
    /**
     * Scenariusz alternatywny 2, punkt 5: Użytkownik wybrał opcje
     * „XX min” (XX jest to liczba minut do wyboru):
     • Po wykonaniu dalszych czynności opisanych w scenariuszu
        „Główny przebieg gry” użytkownicy przeprowadzają
        normalną rozgrywkę z zegarem odmierzającym czas do
        końca przebiegu rozgrywki
     * @throws InterruptedException
     */
    @Test(timeout = 130000)
    public void testTimeLimit() throws InterruptedException {
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
        clickOn(gridPane.getChildren().get(55));
        clickOn(gridPane.getChildren().get(47));

        clickOn(gridPane.getChildren().get(8));
        clickOn(gridPane.getChildren().get(16));
        clickOn(gridPane.getChildren().get(16));
        clickOn(gridPane.getChildren().get(24));


        Thread.sleep(120001);
    }

    /**
     * Nazwa przypadku użycia: Rezygnacja z dalszej gry (RG).
     */
    @Test
    public void testSurrendering() throws InterruptedException {

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

        Button button3 = find("#resignButton");
        clickOn(button3);

        Thread.sleep(10000);

    }
}