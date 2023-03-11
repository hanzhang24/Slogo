package slogo;

import java.awt.Dimension;

import slogo.View.Screens.Screen;
import slogo.View.Screens.SplashScreen;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;


/**
 * main is used to launch game
 */
public class Main extends Application {
    private final String TITLE = "SLogo Team 7";
    private final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
    private final String DEFAULT_LANGUAGE = "English";
    private final ObservableList<String> LANGUAGE_OPTIONS =
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