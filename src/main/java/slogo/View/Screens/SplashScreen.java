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
import slogo.ScreenController;

import java.util.ResourceBundle;


public class SplashScreen extends Screen {
    private String chosenLanguage;
    private Color chosenColor;
    private ColorPicker colorPicker;
    private ComboBox languagePicker;

    ObservableList<String> languageOptions;
    private ScreenController screenController;

    public SplashScreen(Stage stage, String language, ObservableList<String> languageOptions) {
        super(language, stage);
        setScreenLayout("SplashScreenLayout");
        setStylesheet("SplashScreen.css");
        setLayoutResources(ResourceBundle.getBundle(getDEFAULT_RESOURCE_PACKAGE() + getScreenLayout()));
        this.languageOptions = languageOptions;
        this.screenController = new ScreenController(stage);
    }

    public Scene makeScene(int width, int height) {
        String[] indexes;
        setPane(new GridPane());

        Label title = makeLabel("Title", getLabelResources());
        getRoot().getChildren().add(title);

        Node startButton = makeInputPanel(getPanelButtons("NavigationPanel", getPanelResources()), this, getLabelResources(), getReflectionResources());
        getRoot().getChildren().add(startButton);
        startButton.setId("startButton");


        colorPicker = new ColorPicker();
        colorPicker.setId("colorPicker");
        getRoot().getChildren().add(colorPicker);

        languagePicker = new ComboBox(languageOptions);
        languagePicker.setId("languagePicker");
        getRoot().getChildren().add(languagePicker);

        setPositions(getRoot());

        setScene(new Scene(getAllNodes(), width, height));
        getScene().getStylesheets().add(getClass().getResource(getDEFAULT_RESOURCE_FOLDER() + getStylesheet()).toExternalForm());
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