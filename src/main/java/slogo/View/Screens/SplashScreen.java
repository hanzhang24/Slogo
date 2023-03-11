package slogo.View.Screens;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.ScreenController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class SplashScreen extends Screen {

    private final String STYLESHEET = "SplashScreen.css";

    private String chosenLanguage;
    private Color chosenColor;
    private ColorPicker colorPicker;
    private ComboBox languagePicker;

    ObservableList<String> languageOptions;
    private ScreenController screenController;

    public SplashScreen(Stage stage, String language, ObservableList<String> languageOptions) {
        super(language, stage);
        this.languageOptions = languageOptions;
        this.screenController = new ScreenController(stage);
    }

    public Scene makeScene(int width, int height) {
        setPane(new GridPane());

        Label title = makeLabel("Group", getLabelResources());
        getAllObjects().getChildren().add(title);

        Node inputPanel = makeInputPanel(getPanelButtons("NavigationPanel", getPanelResources()), this, getLabelResources(), getReflectionResources());
        getAllObjects().getChildren().add(inputPanel);

        colorPicker = new ColorPicker();
        colorPicker.setId("Color-Selector");
        getAllObjects().getChildren().add(colorPicker);

        languagePicker = new ComboBox(languageOptions);
        languagePicker.setId("Language-Box");
        getAllObjects().getChildren().add(languagePicker);

        setScene(new Scene(getAllObjects(), width, height));
        getScene().getStylesheets().add(getClass().getResource(getDEFAULT_RESOURCE_FOLDER() + STYLESHEET).toExternalForm());
        return getScene();
    }

    public void nextPage() throws ClassNotFoundException {
        try {
            chosenLanguage = languagePicker.getValue().toString();
        } catch (NullPointerException e) {
            throw new NullPointerException("You have not selected an Language");
        }
        chosenColor = colorPicker.getValue();
        screenController.launchGame(chosenColor, chosenLanguage);
    }
}