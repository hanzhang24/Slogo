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
    private static final String REFLECTION_RESOURCES = "ReflectionActions";
    private static final String PANEL_RESOURCES = "PanelActions";
    private static final String STYLESHEET = "SplashScreen.css";

    private ResourceBundle LabelResources;
    private ResourceBundle ReflectionResources;
    private ResourceBundle PanelResources;

    private String chosenLanguage;
    private Color chosenColor;
    private ColorPicker colorPicker;
    private ComboBox languagePicker;

    ObservableList<String> languageOptions;
    private ScreenController screenController;

    private Scene scene;



    public SplashScreen(Stage stage, String language, ObservableList<String> languageOptions, ScreenController screenController) {
        super(stage, language);
        this.languageOptions = languageOptions;
        this.screenController = screenController;

        LabelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        ReflectionResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + REFLECTION_RESOURCES);
        PanelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + PANEL_RESOURCES);
    }

    public Scene makeScene(int width, int height) {
        GridPane root = new GridPane();
        root.getStyleClass().add("grid-pane");
        root.setId("Pane");

        Label title = makeLabel("Group", LabelResources);
        Node inputPanel = makeInputPanel(getPanelButtons("NavigationPanel", PanelResources), this, LabelResources, ReflectionResources);

        colorPicker = new ColorPicker();
        colorPicker.setId("Color-Selector");

        languagePicker = new ComboBox(languageOptions);
        languagePicker.setId("Language-Box");

        root.add(inputPanel, 0, 30);
        root.add(title, 0, 25);
        root.add(colorPicker, 0, 27);
        root.add(languagePicker, 0, 26);

        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        return scene;
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