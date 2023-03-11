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

    private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
    private static final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    private static final String STYLESHEET = "SplashScreen.css";

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
        GridPane root = new GridPane();
        root.getStyleClass().add("grid-pane");
        root.setId("Pane");

        Label title = makeLabel("Group", getLabelResources());
        Node inputPanel = makeInputPanel(getPanelButtons("NavigationPanel", getPanelResources()), this, getLabelResources(), getReflectionResources());

        colorPicker = new ColorPicker();
        colorPicker.setId("Color-Selector");

        languagePicker = new ComboBox(languageOptions);
        languagePicker.setId("Language-Box");

        //GET RID OF MAGIC NUMBERS
        root.add(inputPanel, 0, 30);
        root.add(title, 0, 25);
        root.add(colorPicker, 0, 27);
        root.add(languagePicker, 0, 26);

        setScene(new Scene(root, width, height));
        getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
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