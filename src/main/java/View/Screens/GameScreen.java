package View.Screens;

import View.DrawBoardView;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameScreen extends Screen {

  public static final String GAMESCREEN_STYLESHEET = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/" + "GameScreen.css");


  public GameScreen(Stage stage, String language) {
    super(stage, language);
    this.root = new GridPane();
  }

  @Override
  public Scene makeScene(int width, int height) {
    root = new GridPane();
    root.getStyleClass().add("grid-pane");
    root.setId("Pane");
    DrawBoardView canvas = new DrawBoardView();
    root.getChildren().add(canvas.getContainer());
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
