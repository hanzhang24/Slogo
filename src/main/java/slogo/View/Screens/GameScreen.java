package slogo.View.Screens;

import javafx.animation.Animation;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
  private Group all;

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
    avatar = new Turtle();
    animations = new Animator(avatar);
    all = new Group();
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
    avatar.updateColor(newcolor);
  }

  public void updateAvatarPosXY(double newX, double newY) {
    // animations.makeTranslation(newX, newY);
    double xCor = avatar.getXCor();
    double yCor = avatar.getYCor();
    avatar.updatePosXY(newX, newY);
    if(avatar.getPenActive()){
      all.getChildren().add(new Line(xCor + 25, yCor + 25, newX + 300, -1 * newY + 300));
    }
  }

  public void updateAvatarRot(double newRot) {
    avatar.updateRot(newRot);
  }

  public CommandBoxView getCommandBoxView(){
    return commandBoxView;
  }

  public AvatarView getAvatar(){
    return avatar;
  }
}
