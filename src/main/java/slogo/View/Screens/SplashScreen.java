package slogo.View.Screens;

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


public class SplashScreen {

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

    private Screen nextScreen;

    private ColorPicker colorPicker;
    private ComboBox languagePicker;
    ObservableList<String> languageOptions;
    private ScreenController screenController;
    private Stage stage;
    private Scene scene;



    public SplashScreen(Stage stage, String language, ObservableList<String> languageOptions, ScreenController screenController) {
        this.stage = stage;
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

        Label title = makeLabel("Group");
        Node inputPanel = makeInputPanel(getPanelButtons("NavigationPanel"));

        colorPicker = makeColorPicker("Picker");
        colorPicker.setId("Color-Selector");
        languagePicker = makeLanguagePicker(languageOptions);
        languagePicker.setId("Language-Box");


        root.add(inputPanel, 0, 30);
        root.add(title, 0, 25);
        root.add(colorPicker, 0, 27);
        root.add(languagePicker, 0, 26);

        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        return scene;
    }


    private void nextPage() throws ClassNotFoundException {
        try {
            chosenLanguage = languagePicker.getValue().toString();
        } catch (NullPointerException e) {
            throw new NullPointerException("You have not selected an Language");
        }
        chosenColor = colorPicker.getValue();
        nextScreen = new GameScreen(this.stage, chosenLanguage, chosenColor);
        stage.setScene(nextScreen.makeScene(750, 750));

        screenController.setGameScreen((GameScreen) nextScreen);
    }

    public String getChosenLanguage(){
        return chosenLanguage;
    }
    public Color getChosenColor(){
        return chosenColor;
    }

    protected Label makeLabel (String property) {
        Label label = new Label(LabelResources.getString(property));
        return label;
    }


    protected ColorPicker makeColorPicker (String id) {
        ColorPicker picker = new ColorPicker();
        return picker;
    }

    protected ComboBox makeLanguagePicker (ObservableList<String> options) {
        ComboBox languageComboBox = new ComboBox(options);
        return languageComboBox;
    }

    // get button actions for each panel from resource file
    private List<String> getPanelButtons (String property) {
        return Arrays.asList(PanelResources.getString(property).split(","));
    }

    private Node makeInputPanel (List<String> actions) {
        HBox result = new HBox();
        // create buttons, with their associated actions
        for (String a : actions) {
            result.getChildren().add(makeButton(a));
        }
        return result;
    }

    // makes a button using either an image or a label
    private Button makeButton (String property) {
        // represent all supported image suffixes
        Button result = new Button();
        String label = LabelResources.getString(property);
        result.setText(label);
        // turn given string into method call
        result.setOnAction(handler -> {
                    try {
                        String methodName = ReflectionResources.getString(property);
                        Method m = SplashScreen.this.getClass().getDeclaredMethod(methodName);
                        m.invoke(SplashScreen.this);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        result.setId(property);
        return result;
    }

}