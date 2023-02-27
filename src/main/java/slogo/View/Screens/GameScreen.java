package slogo.View.Screens;

import javafx.animation.Animation;
import slogo.View.Animator;
import slogo.View.AvatarView;
import slogo.View.Avatars.Turtle;
import slogo.View.Containers.CommandBoxView;
import slogo.View.DrawBoardView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GameScreen extends Screen {

  public static final String GAMESCREEN_STYLESHEET = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/" + "GameScreen.css");

  private static Color color;
  private AvatarView avatar;

  private Animator animations;
  private CommandBoxView commandBoxView;

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
    commandBoxView = new CommandBoxView();
    root.getChildren().add(commandBoxView.getContainer());
    GridPane.setConstraints(commandBoxView.getContainer(), 0, 1);
    AvatarView avatar = new Turtle();
    animations = new Animator(avatar);
    Group all = new Group();
    all.getChildren().add(root);
    all.getChildren().add(avatar.getView());
    this.scene = new Scene(all, width, height);
    scene.getStylesheets().add(getClass().getResource(GAMESCREEN_STYLESHEET).toExternalForm());
    return scene;
  }

  public void updateAvatarIsPenDown(boolean penStatus) {
    avatar.updatePen(penStatus);
  }
  public void updatePenColor(Color newcolor) {
  }

  public void updateAvatarPosXY(double newX, double newY) {
    animations.makeTranslation(newX, newY);
    avatar.updatePosXY(newX, newY);
  }

  public void updateAvatarRot(double newRot) {
    avatar.updateRot(newRot);
  }

  public CommandBoxView getCommandBoxView(){
    return commandBoxView;
  }

}
