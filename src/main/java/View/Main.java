package View;

import java.awt.Dimension;

import View.Screens.SplashScreen;
import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {
    public static final String TITLE = "SLogo Team 6";
    public static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
    public static final String DEFAULT_LANGUAGE = "English";

    @Override
    public void start (Stage stage) {
        SplashScreen splashScreen = new SplashScreen(stage, DEFAULT_LANGUAGE);
        stage.setScene(splashScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
        stage.setTitle(TITLE);
        stage.show();
    }
}