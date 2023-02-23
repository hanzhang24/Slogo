package View.Screens;

import View.DrawBoardView;
import View.Screen;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
public class GameScreen extends Screen {

  public static final String STYLESHEET = "default.css";
  public GameScreen(Stage primaryStage){
    this.root = new GridPane();
    this.stage = primaryStage;
  }
  @Override
  public Scene makeScene() {
    DrawBoardView canvas = new DrawBoardView();
    this.root.getChildren().add(canvas.getCanvas());
    Group Root = new Group();
    Scene primaryScene = new Scene(Root, Double.parseDouble(this.resources.getString("Width")), Double.parseDouble(this.resources.getString("Height")));
    primaryScene.getStylesheets().add(STYLESHEET);
    return primaryScene;
  }
}
