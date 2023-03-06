package slogo;

import java.awt.Dimension;

import slogo.View.Screens.Screen;
import slogo.View.Screens.SplashScreen;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;



public class Main extends Application {
    private static final String TITLE = "SLogo Team 7";
    private static final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
    private static final String DEFAULT_LANGUAGE = "English";
    private static final ObservableList<String> LANGUAGE_OPTIONS =
            FXCollections.observableArrayList(
                    "English",
                    "Spanish"
            );
    @Override
    public void start (Stage stage) {
        ScreenController screenController = new ScreenController(stage);
        SplashScreen splashScreen = new SplashScreen(stage, DEFAULT_LANGUAGE, LANGUAGE_OPTIONS, screenController);
        stage.setScene(splashScreen.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
        stage.setTitle(TITLE);
        stage.show();
    }
}