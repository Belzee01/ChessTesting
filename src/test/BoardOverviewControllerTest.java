package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sample.GameEngine;
import sample.Main;
import sample.controllers.BoardOverviewController;
import sample.controllers.GameCreatingController;
import sample.models.Images;
import sample.services.TCPServerConnectionService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.InvalidMarkException;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class BoardOverviewControllerTest {

    private BorderPane rootLayout;
    Images images = new Images();
    private BoardOverviewController boardOverviewController;
    private TCPServerConnectionService tcpServerConnectionService = new TCPServerConnectionService(50000);

    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        // Initialise Java FX

        System.out.printf("About to launch FX App\n");
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(Main.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        System.out.printf("FX App thread started\n");
        Thread.sleep(500);
    }

    @Before
    public void setUp(){
        try{
            GameEngine.getInstance().setTcpConnectionService(this.tcpServerConnectionService);
            GameEngine.getInstance().setServerRole(true);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameCreatingController.class.getResource("../view/BoardOverview.fxml"));
//            rootLayout.setCenter(loader.load());
            loader.load();

            // Give the controller access to the main app.

            boardOverviewController = loader.getController();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testInitBoardWithNullArguments() throws Exception {
        String oddColor = null;
        String evenColor = null;
        boardOverviewController.initBoard();
    }



    @Test
    public void testShowMovesWithNullValue() throws Exception {
        Method method = BoardOverviewController.class.getDeclaredMethod("showMoves", ImageView.class);
        method.setAccessible(true);

        ImageView imageView = null;
        method.invoke(boardOverviewController, imageView);
    }

    @Test
    public void testMoveWithNullValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = BoardOverviewController.class.getDeclaredMethod("move", ImageView.class);
        method.setAccessible(true);

        ImageView imageView = new ImageView(images.getMoveImage(0, 0));
        method.invoke(boardOverviewController, imageView);
    }
}