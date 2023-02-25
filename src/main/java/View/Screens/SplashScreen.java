package View.Screens;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SplashScreen extends Screen {

    public static final String SPLASHSCREEN_STYLESHEET = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/" + "SplashScreen.css");


    public SplashScreen(Stage stage, String language) {
        super(stage, language);
    }

    @Override
    public Scene makeScene(int width, int height) {
        GridPane root = new GridPane();
        root.getStyleClass().add("grid-pane");
        root.setId("Pane");

        Node label = makeLabel("Group");

        Node buttons = makeActions(
                makeButton("Start", e -> nextScreen())
        );
        root.add(buttons, 0, 30);
        root.add(label, 0, 25);

        this.scene = new Scene(root, width, height);

        scene.getStylesheets().add(getClass().getResource(SPLASHSCREEN_STYLESHEET).toExternalForm());
        return scene;
    }

    private void nextScreen() {
        GameScreen nextScreen = new GameScreen(this.stage, "English");
        stage.setScene(nextScreen.makeScene(750, 750));
    }

}