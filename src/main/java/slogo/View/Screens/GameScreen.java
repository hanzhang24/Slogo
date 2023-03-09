package slogo.View.Screens;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import slogo.View.Animator;
import slogo.View.Containers.HistoryView;
import slogo.View.Containers.SliderView;
import slogo.View.PenView;
import slogo.View.Avatars.Turtle;
import slogo.View.Containers.CommandBoxView;
import slogo.View.DrawBoardView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GameScreen extends Screen implements ModelView{

  public static final String GAMESCREEN_STYLESHEET = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/" + "GameScreen.css");

  private  Color color;
  private PenView avatar;
  private Animator animations;
  private CommandBoxView commandBoxView;
  private HistoryView historyView;
  private DrawBoardView canvas;
  private Group all;
  private ResourceBundle LabelResources;
  private ResourceBundle ReflectionResources;
  private ResourceBundle PanelResources;
  private static final String REFLECTION_RESOURCES = "ReflectionActions";
  private static final String PANEL_RESOURCES = "PanelActions";

  public GameScreen(Stage stage, String language, Color color) {
    super(stage, language);
    this.root = new GridPane();
    this.color = color;
    all = new Group();

    LabelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    ReflectionResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + REFLECTION_RESOURCES);
    PanelResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + PANEL_RESOURCES);
  }

  public double getAnimationSpeed() {
    return animations.getAnimationSpeed();
  }

  @Override
  public Scene makeScene(int width, int height) {
    setUpGridPane();
    createCanvas();
    MakeTurtle();
    createCommandBox();
    createButtions();
    createHistoryView();

    this.scene = new Scene(all, width, height);
    scene.getStylesheets().add(getClass().getResource(GAMESCREEN_STYLESHEET).toExternalForm());

    return scene;
  }

  private void createHistoryView() {
    historyView = new HistoryView();
    root.getChildren().add(historyView.getHistoryContainer());
    GridPane.setConstraints(historyView.getHistoryContainer(), 1,0);
  }

  private void createButtions() {
    List<String> actions = new ArrayList<>();
    actions.add("Step");
    actions.add("Pause");
    actions.add("Reset");
    HBox container = makeInputPanel(actions, this, LabelResources, ReflectionResources);
    container.setId("Animation-Panel");

    SliderView animationInputs  = new SliderView(animations);
    container.getChildren().add(animationInputs.getSliderContainer());
    root.getChildren().add(container);
    GridPane.setConstraints(container, 0, 1);

  }

  private void createCommandBox() {
    commandBoxView = new CommandBoxView(animations);
    root.getChildren().add(commandBoxView.getCommandContainer());
    GridPane.setConstraints(commandBoxView.getCommandContainer(), 0, 2);
  }

  private void createCanvas() {
    canvas = new DrawBoardView();
    canvas.setColor(this.color);
    root.getChildren().add(canvas.getContainer());
    GridPane.setConstraints(canvas.getContainer(), 0, 0);
  }

  private void MakeTurtle() {
    avatar = new Turtle();
    avatar.getImage().toBack();
    all.getChildren().add(avatar.getImage());
    animations = new Animator(avatar);
  }

  private void setUpGridPane() {
    root = new GridPane();
    root.getStyleClass().add("grid-pane");
    root.setId("Pane");
    all.getChildren().add(root);
  }

  public void updateAvatarIsPenDown(boolean penStatus) {
    avatar.updatePen(penStatus);
  }

  @Override
  public void updateAvatarPenColor(int red, int green, int blue) {
    Color newColor = Color.rgb(red, green, blue);
    avatar.updateColor(newColor);
  }
  public void updateAvatarPosXY(double newX, double newY) {
    newX = newX + 250;
    newY = -1*newY + 250; // converts to View coordinates
    double OldXCor = avatar.getXCor();
    double OldYCor = avatar.getYCor();
    animations.makeTranslation(newX, newY);
    animations.runAnimation();
    avatar.setCoordinates(newX, newY);
    if(avatar.getPenActive()){
//      all.getChildren().add(new Line(xCor + 25, yCor + 25, newX + 300,  newY + 300));
        canvas.draw(OldXCor, OldYCor, avatar.getXCor(), avatar.getYCor());
    }
  }
  public void updateAvatarRot(double newRot) {
    double oldRot = avatar.getRot();
    double saved = newRot;
    newRot = -1*newRot + 90;
    newRot = (newRot-oldRot);
    animations.makeRotation(newRot);
    animations.runAnimation();
    avatar.updateRot(saved);
  }

  @Override
  public void updateAvatarVisible(boolean state) {
    avatar.changeVisible();
  }

  @Override
  public void clearScreen() {
    canvas.clear();
  }

  @Override
  public void updateDisplayedHistory(String userInput) {
    historyView.updateCommandHistory(userInput);
  }
  @Override
  public void displayReturnValues(List<String> returnValues) {
  }

  @Override
  public void addToUserLibrary(String functionDescription) {
    historyView.updateLibraryHistory(functionDescription);
  }
  public CommandBoxView getCommandBoxView(){
    return commandBoxView;
  }
  public PenView getAvatar(){
    return avatar;
  }

  public void reset(){
    clearScreen();
  }
  public void pause(){
    animations.pause();
  }
  public void step(){
    animations.step();
  }
}
