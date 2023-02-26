package View.Screens;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.awt.*;

public class GameScreen extends Screen {

  public static final String GAMESCREEN_STYLESHEET = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/" + "GameScreen.css");

  private static Color color;

  public GameScreen(Stage stage, String language) {
    super(stage, language);
  }

  public GameScreen(Stage stage, String language, Color color) {
    super(stage, language);
    this.root = new GridPane();
    this.color = color;
  }

  @Override
  public Scene makeScene(int width, int height) {
    GridPane root = new GridPane();
    root.getStyleClass().add("grid-pane");
    root.setId("Pane");
    this.scene = new Scene(root, width, height);

    scene.getStylesheets().add(getClass().getResource(GAMESCREEN_STYLESHEET).toExternalForm());
    return scene;

//    Node label = makeLabel("Group");
//    DrawBoardView canvas = new DrawBoardView();
//    this.root.getChildren().add(canvas.getCanvas());
//    Group Root = new Group();
//    Scene primaryScene = new Scene(Root, Double.parseDouble(this.resources.getString("Width")), Double.parseDouble(this.resources.getString("Height")));
//    primaryScene.getStylesheets().add(GAMESCREEN_STYLESHEET);
//    return primaryScene;
  }

}
