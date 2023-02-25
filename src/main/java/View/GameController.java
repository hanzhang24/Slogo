package View;

import java.awt.Dimension;
import javafx.application.Application;
import javafx.stage.Stage;


public class GameController extends Application {
    public static final String TITLE = "Slogo Group 7";
    public static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
    public static final String DEFAULT_LANGUAGE = "English";


    @Override
    public void start (Stage stage) {
        Screen SplashScreen = new SplashScreen(stage);
        stage.setScene(SplashScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
        stage.setTitle(TITLE);
        stage.show();
    }
}