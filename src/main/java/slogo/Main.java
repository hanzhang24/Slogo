package slogo;

import java.awt.Dimension;

import slogo.View.Screens.SplashScreen;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;



public class Main extends Application {
    @Override
    public void start (Stage stage) {
        SlogoInitializer slogoInitializer = new SlogoInitializer(stage);
    }
}