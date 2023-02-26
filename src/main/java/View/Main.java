package View;

import java.awt.Dimension;

import View.Screens.GameScreen;
import View.Screens.SplashScreen;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Screen;
import javafx.stage.Stage;



public class Main extends Application {
    private static final String TITLE = "SLogo Team 6";
    private static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
    private static final String DEFAULT_LANGUAGE = "English";
    private static final ObservableList<String> LANGUAGE_OPTIONS  =
            FXCollections.observableArrayList(
                    "English",
                    "Spanish"
            );

    @Override
    public void start (Stage stage) {
        SplashScreen splashScreen = new SplashScreen(stage, DEFAULT_LANGUAGE, LANGUAGE_OPTIONS);
        stage.setScene(splashScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
        stage.setTitle(TITLE);
        stage.show();
    }
}