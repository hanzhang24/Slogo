package slogo.View.Screens;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
import javafx.scene.paint.Color;

public class GameScreen extends Screen implements ModelView{

  private static final String DEFAULT_RESOURCE_PACKAGE = "View.";
  private static final String DEFAULT_RESOURCE_FOLDER = "/"+DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String STYLESHEET = "GameScreen.css";
  private static final String GAME_SCREEN_LAYOUT = "GameScreenLayout";

  private ResourceBundle LayoutResources;

  private Color color;
  private PenView avatar;
  private Animator animations;
  private CommandBoxView commandBoxView;
  private HistoryView historyView;
  private DrawBoardView canvas;
  private Group all;

  public GameScreen(String language, Color color) {
    super(language);
    LayoutResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + GAME_SCREEN_LAYOUT);
    setRoot(new GridPane());
    this.color = color;
    all = new Group();
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
    createButtons();
    createHistoryView();

    setScene(new Scene(all, width, height));
    getScene().getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    return getScene();
  }

  private void createHistoryView() {
    historyView = new HistoryView();
    VBox container = historyView.make(getPanelButtons("DropDownPanel", getPanelResources()), getLabelResources());
    container.setId("History-Container");
    String[] indexes = LayoutResources.getString("HistoryView").split(",");
    setIndexes(indexes, container);
  }

  private void setIndexes(String[] indexes, Pane node) {
    getRoot().getChildren().add(node);
    GridPane.setConstraints(node, Integer.parseInt(indexes[0]),Integer.parseInt(
        indexes[1]));
  }

  private void createButtons() {
    HBox container = makeInputPanel(getPanelButtons("GameScreenNavigationPanel", getPanelResources()), this, getLabelResources(), getReflectionResources());
    container.setId("Animation-Panel");

    SliderView animationInputs  = new SliderView(animations);
    container.getChildren().add(animationInputs.getSliderContainer());
    String[] indexes = LayoutResources.getString("ButtonPanel").split(",");
    setIndexes(indexes, container);
  }

  private void createCommandBox() {
    commandBoxView = new CommandBoxView(animations);
    String[] indexes = LayoutResources.getString("CommandBox").split(",");
    setIndexes(indexes, commandBoxView.getCommandContainer());
  }

  private void createCanvas() {
    canvas = new DrawBoardView();
    canvas.setColor(this.color);
    String[] indexes = LayoutResources.getString("Canvas").split(",");
    setIndexes(indexes, canvas.getContainer());
  }

  private void MakeTurtle() {
    avatar = new Turtle();
    avatar.getImage().toBack();
    all.getChildren().add(avatar.getImage());
    animations = new Animator(avatar);
  }

  private void setUpGridPane() {
    setRoot(new GridPane());
    getRoot().getStyleClass().add("grid-pane");
    getRoot().setId("Pane");
    all.getChildren().add(getRoot());
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

  private void reset(){
    clearScreen();
  }
  private void pause(){
    animations.pause();
  }
  private void step(){
    animations.step();
  }
  public void run(){ commandBoxView.sendText(); }
  public void clear(){ commandBoxView.clear(); }

  public void initializeSequentialTransition(){
    animations.resetAnimations();
  }
  public void playSequentialTransition(){
    animations.runAnimation();
  }
  public void updatePenColor(Color penTest) {
    this.avatar.updateColor(penTest);
  }
}
