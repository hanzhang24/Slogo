package slogo.View.Screens;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SplashScreen extends Screen {

    public static final String SPLASHSCREEN_STYLESHEET = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/" + "SplashScreen.css");

    private String chosenLanguage;
    private Color chosenColor;

    private Screen nextScreen;

    private Label label;
    private Node buttons;
    private ColorPicker colorPicker;
    private ComboBox languagePicker;
    ObservableList<String> languageOptions;


    public SplashScreen(Stage stage, String language, ObservableList<String> languageOptions) {
        super(stage, language);
        this.languageOptions = languageOptions;
    }

    @Override
    public Scene makeScene(int width, int height) {
        GridPane root = new GridPane();
        root.getStyleClass().add("grid-pane");
        root.setId("Pane");

        label = makeLabel("Group");
        buttons = makeActions(
                makeButton("Start", e -> startClicked())
        );
        buttons.setId("Go-Button");
        colorPicker = makeColorPicker("Picker");
        colorPicker.setId("Color-Selector");
        languagePicker = makeLanguagePicker(languageOptions);
        languagePicker.setId("Language-Box");


        root.add(buttons, 0, 30);
        root.add(label, 0, 25);
        root.add(colorPicker, 0, 27);
        root.add(languagePicker, 0, 26);

        scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource(SPLASHSCREEN_STYLESHEET).toExternalForm());
        return scene;
    }


    private void startClicked() {
        chosenLanguage = languagePicker.getValue().toString();
        chosenColor = colorPicker.getValue();
        nextScreen = new GameScreen(this.stage, chosenLanguage, chosenColor);
        stage.setScene(nextScreen.makeScene(750, 750));
    }
    public String getChosenLanguage(){
        return chosenLanguage;
    }
    public Color getChosenColor(){
        return chosenColor;
    }
}