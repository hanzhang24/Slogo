package View.Screens;

import View.Screen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class SplashScreen extends Screen {
    public static final String DEFAULT_RESOURCE_PACKAGE = "example.";
    public static final String DEFAULT_STYLESHEET = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/")+"Default.css";

    private ResourceBundle myResources;
    Stage stage;

    public SplashScreen(Stage stage) {
        stage = stage;
    }

    public Scene makeScene(int width, int height) {
        GridPane root = new GridPane();
        Node label = makeLabel("Group 7");
        root.add(label, 1, 0);

        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());

        return scene;
    }

    private Node makeActions (Node ... buttons) {
        HBox panel = new HBox();
        panel.getStyleClass().add("button-box");
        panel.getChildren().addAll(buttons);
        return panel;
    }

    private Node makeButton (String property, EventHandler<ActionEvent> response) {
        Button result = new Button();
        result.setText(myResources.getString(property));
        result.setOnAction(response);
        return setID(property, result);
    }

    private Node makeLabel (String property) {
        Label label = new Label(myResources.getString(property));
        return setID(property, label);
    }

    private Node setID (String id, Node node) {
        node.setId(id);
        return node;
    }
}