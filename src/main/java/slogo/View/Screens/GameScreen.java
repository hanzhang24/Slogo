package slogo.View.Screens;

import slogo.View.AvatarView;
import slogo.View.Avatars.Turtle;
import slogo.View.Containers.CommandBoxView;
import slogo.View.DrawBoardView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

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
    root = new GridPane();
    root.getStyleClass().add("grid-pane");
    root.setId("Pane");
    DrawBoardView canvas = new DrawBoardView();
    root.getChildren().add(canvas.getContainer());
    CommandBoxView commandBox = new CommandBoxView();
    root.getChildren().add(commandBox.getContainer());
    GridPane.setConstraints(commandBox.getContainer(), 0, 1);
    AvatarView avatar = new Turtle();
    Group all = new Group();
    all.getChildren().add(root);
    all.getChildren().add(avatar.getImage());
    this.scene = new Scene(all, width, height);
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
